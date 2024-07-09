package com.notesapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.notesapp.api.NotesApiService
import com.notesapp.api.RetrofitInstance
import com.notesapp.repository.NotesRepository
import com.notesapp.room.NotesAppDataBase

class NotesViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesSharedViewModel::class.java)) {
            val database = NotesAppDataBase.getInstance(context)
            val notesDao = database.noteDao
            val notesApiService = RetrofitInstance.getInstance()
                .create(NotesApiService::class.java)
            val repository = NotesRepository(notesDao, notesApiService)
            return NotesSharedViewModel(repository) as T
        }

        return super.create(modelClass)
    }
}