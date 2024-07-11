package com.notesapp.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.models.Note
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoteDetailsViewModel(
    private val notesSharedViewModel: NotesSharedViewModel,
) : ViewModel() {
    private val repository = notesSharedViewModel.repository
    var isValid = MediatorLiveData<Boolean>()
    var noteTitle = MutableLiveData<String>()
    var noteBody = MutableLiveData<String>()
    val createdDate = MutableLiveData<String>()
    lateinit var userId: String

    init {
        isValid.addSource(noteTitle) {
            isValid.value = checkIfValid()
        }
        isValid.addSource(noteBody) {
            isValid.value = checkIfValid()
        }
        actualDate()
    }

    fun updateTexts() {
        noteTitle.value = notesSharedViewModel.selectedNote?.title
        noteBody.value = notesSharedViewModel.selectedNote?.body
    }

    private fun insert(note: Note) = viewModelScope.launch {
        repository.insertToApi(note)
    }

    private fun update(note: Note) = viewModelScope.launch {
//        repository.update(note)
        repository.updateToApi(note)
    }

    suspend fun save() {
        if (notesSharedViewModel.selectedNote == null) {
            if (!(noteTitle.value).isNullOrBlank() && !(noteBody.value).isNullOrBlank()) {
                val date = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
                val formattedDate = date.format(formatter)
                userId = repository.getUserId()
                insert(
                    Note(
                        "",
                        noteTitle.value!!,
                        10.0f,
                        10.0f,
                        userId,
                        formattedDate,
                        noteBody.value!!
                    )
                )
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

    private fun actualDate() {
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val formattedDate = date.format(formatter)
        createdDate.value = "Creation date: $formattedDate"
    }

    private fun checkIfValid() = !(noteTitle.value).isNullOrBlank() &&
            !(noteBody.value).isNullOrBlank()
}