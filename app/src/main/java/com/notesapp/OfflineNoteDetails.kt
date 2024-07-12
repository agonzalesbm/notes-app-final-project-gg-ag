package com.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.notesapp.databinding.FragmentOfflineNoteDetailsBinding
import com.notesapp.viewmodels.NoteDetailsViewModel
import com.notesapp.viewmodels.NotesSharedViewModel

class OfflineNoteDetails : Fragment(R.layout.fragment_offline_note_details) {
    lateinit var binding: FragmentOfflineNoteDetailsBinding
    lateinit var viewModel: NotesSharedViewModel
    lateinit var detailsViewModel: NoteDetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfflineNoteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).notesViewModel
        detailsViewModel = (activity as MainActivity).notesDetailsViewModel
        binding.viewModel = viewModel
        binding.detailsViewModel = detailsViewModel
        binding.lifecycleOwner = this
        detailsViewModel.updateTexts()
        binding.backButton.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_offlineNoteDetails_to_homeFragment)
        }
    }
}