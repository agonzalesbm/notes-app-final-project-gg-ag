package com.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.notesapp.adapters.NotesRecyclerViewAdapter
import com.notesapp.databinding.FragmentHomeBinding
import com.notesapp.models.Note
import com.notesapp.network.ConnectivityObserver
import com.notesapp.network.NetworkConnectivityObserver
import com.notesapp.viewmodels.NotesSharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: NotesSharedViewModel
    lateinit var adapter: NotesRecyclerViewAdapter
    lateinit var connectivityObserver: ConnectivityObserver
    private var isNetworkAvailable = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).notesViewModel
        viewModel.getAllNotes()
        connectivityObserver = NetworkConnectivityObserver(requireContext())
        setupRecyclerView()
        setupAddButton()
        logoutButton()
        connection()
    }

    private fun connection() {
        lifecycleScope.launch {
            connectivityObserver.observer().collect {
                if (it != ConnectivityObserver.Status.Available) {
                    withContext(Dispatchers.Main) {
                        println("No internet")
                        isNetworkAvailable = false
                        println("onCreate $isNetworkAvailable")
                        binding.floatingActionButton.isEnabled = false
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        println("Available")
                        isNetworkAvailable = true
                        println("onCreate $isNetworkAvailable")
                        binding.floatingActionButton.isEnabled = true
                    }
                }
            }
        }
    }

    private fun logoutButton() {
        binding.logoutButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logOut()
            }
            binding.root.findNavController()
                .navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    private fun setupAddButton() {
        binding.floatingActionButton.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToNoteDetailFragment()
            binding.root.findNavController().navigate(direction)
        }
    }

    private fun setupRecyclerView() {
        adapter = NotesRecyclerViewAdapter(
            notes = listOf(),
            clickListener = { note ->
                viewModel.selectedNote(note)
                if (isNetworkAvailable) {
                    val direction1 = HomeFragmentDirections.actionHomeFragmentToNoteDetailFragment()
                    binding.root.findNavController().navigate(direction1)
                } else {
                    val direction = HomeFragmentDirections.actionHomeFragmentToOfflineNoteDetails()
                    binding.root.findNavController().navigate(direction)
                }
            },
            longClickListener = { view, note ->
                showPopup(view, note)
            }
        )
        val ownerContext = (activity as MainActivity)
        binding.recyclerView.layoutManager = GridLayoutManager(ownerContext, 3)
        binding.recyclerView.adapter = adapter
        activity.let {
            viewModel.notes.observe(viewLifecycleOwner) { notes ->
                adapter.notes = notes
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun showPopup(view: View, note: Note) {
        val popup = PopupMenu(view.context, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_delete, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            handleMenuItemClick(menuItem, note)
            true
        }
        popup.show()
    }

    private fun handleMenuItemClick(menuItem: MenuItem, note: Note) {
        when (menuItem.itemId) {
            R.id.delete -> {
                println(note.toString())
                viewModel.deleteMenuItem(note)
            }
        }
    }
}