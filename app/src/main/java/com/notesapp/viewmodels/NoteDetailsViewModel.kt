package com.notesapp.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.models.Note
import kotlinx.coroutines.launch

class NoteDetailsViewModel(private val notesSharedViewModel: NotesSharedViewModel): ViewModel() {
    private val repository = notesSharedViewModel.repository
    var isValid = MediatorLiveData<Boolean>()
    var noteTitle = MutableLiveData<String>()
    var noteBody = MutableLiveData<String>()

    init {
        isValid.addSource(noteTitle) {
            isValid.value = checkIfValid()
        }
        isValid.addSource(noteBody) {
            isValid.value = checkIfValid()
        }
    }

    fun updateTexts() {
        noteTitle.value = notesSharedViewModel.selectedNote?.title
        noteBody.value = notesSharedViewModel.selectedNote?.body
    }

    private fun insert(note: Note) = viewModelScope.launch {
        repository.insertToApi(note)
    }

    private fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun save() {
        if (notesSharedViewModel.selectedNote == null) {
            if (!(noteTitle.value).isNullOrBlank() && !(noteBody.value).isNullOrBlank()) {
                insert(Note("", noteTitle.value!!, 0.0f, 0.0f, "", "", noteBody.value!!))
                noteTitle.value = ""
                noteBody.value = ""
            }
        } else {
            if (!(noteTitle.value).isNullOrBlank() && !(noteBody.value).isNullOrBlank()) {
                notesSharedViewModel.selectedNote?.title = noteTitle.value!!
                notesSharedViewModel.selectedNote?.body = noteBody.value!!
                update(notesSharedViewModel.selectedNote!!)
                notesSharedViewModel.selectedNote = null
                noteTitle.value = ""
                noteBody.value = ""
            }
        }
    }

    private fun checkIfValid() = !(noteTitle.value).isNullOrBlank() &&
            !(noteBody.value).isNullOrBlank()
}