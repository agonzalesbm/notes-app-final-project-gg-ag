package com.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.notesapp.databinding.FragmentNoteDetailBinding
import com.notesapp.viewmodels.NoteDetailsViewModel
import com.notesapp.viewmodels.NotesSharedViewModel

class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {
    lateinit var binding: FragmentNoteDetailBinding
    lateinit var viewModel: NotesSharedViewModel
    lateinit var detailsViewModel: NoteDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
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
        binding.saveButton.setOnClickListener {
            detailsViewModel.save()
            binding.root.findNavController().navigate(R.id.action_noteDetailFragment_to_homeFragment)
        }
    }
}