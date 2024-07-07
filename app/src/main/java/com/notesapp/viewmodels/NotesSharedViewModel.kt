package com.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import com.notesapp.models.Note
import com.notesapp.repository.NotesRepository

class NotesSharedViewModel(val repository: NotesRepository): ViewModel() {
    var selectedNote: Note? = null
    val notes = repository.notes

    fun selectedNote(note: Note) {
        selectedNote = note
    }
}