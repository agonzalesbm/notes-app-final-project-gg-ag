package com.notesapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.notesapp.api.NotesApiService
import com.notesapp.api.RetrofitInstance
import com.notesapp.repository.LoginRepository

class LoginShareViewModelFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginShareViewModel::class.java)) {
            val apiService = RetrofitInstance.getInstance().create(NotesApiService::class.java)
            val repository = LoginRepository(apiService)
            return LoginShareViewModel(repository) as T
        }
        return super.create(modelClass)
    }
}