package com.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.models.Note
import com.notesapp.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesSharedViewModel(val repository: NotesRepository): ViewModel() {
    var selectedNote: Note? = null
    val notes = repository.notes

    fun selectedNote(note: Note) {
        selectedNote = note
    }

    fun getAllNotes() = viewModelScope.launch {
        repository.getAll().collect() {result ->
            if (!result) {

            }
        }
    }
}