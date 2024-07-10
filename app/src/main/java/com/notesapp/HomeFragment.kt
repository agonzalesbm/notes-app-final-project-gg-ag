package com.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.notesapp.adapters.NotesRecyclerViewAdapter
import com.notesapp.databinding.FragmentHomeBinding
import com.notesapp.models.Note
import com.notesapp.viewmodels.NotesSharedViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: NotesSharedViewModel
    lateinit var adapter: NotesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).notesViewModel
        viewModel.getAllNotes()
        setupRecyclerView()
        setupAddButton()
        setupProfileButton()
    }

    private fun setupProfileButton() {
        binding.profileButton.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
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
                val direction = HomeFragmentDirections.actionHomeFragmentToNoteDetailFragment()
                binding.root.findNavController().navigate(direction)
            },
            longClickListener = { view, note ->
                showPopup(view, note)
            }
        )
        val ownerContext = (activity as MainActivity)
        binding.recyclerView.layoutManager = GridLayoutManager(ownerContext,3)
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