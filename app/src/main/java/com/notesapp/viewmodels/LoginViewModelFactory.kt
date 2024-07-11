package com.notesapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.notesapp.api.NotesApiService
import com.notesapp.api.RetrofitInstance
import com.notesapp.repository.LoginRepository

class LoginViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val notesApiService = RetrofitInstance.getInstance()
                .create(NotesApiService::class.java)
            val loginRepository = LoginRepository(notesApiService, context)
            return LoginViewModel(loginRepository) as T
        }
        return super.create(modelClass)
    }
}