package com.notesapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.notesapp.repository.NotesRepository
import com.notesapp.room.NotesAppDataBase

class NotesViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesSharedViewModel::class.java)) {
            val database = NotesAppDataBase.getInstance(context)
            val contactsDao = database.noteDao
            val repository = NotesRepository(contactsDao)
            return NotesSharedViewModel(repository) as T
        }

        return super.create(modelClass)
    }
}