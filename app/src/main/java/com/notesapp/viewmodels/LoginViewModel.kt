package com.notesapp.viewmodels

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.notesapp.models.User

class LoginViewModel(val loginShareViewModel: LoginShareViewModel): ViewModel() {
    var isValid = MediatorLiveData<Boolean>()
    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    private val repository = loginShareViewModel.repository

    init {
        isValid.addSource(username) {
            isValid.value = checkIfValid()
        }
        isValid.addSource(password) {
            isValid.value = checkIfValid()
        }
    }

    private fun checkIfValid(): Boolean {
        return !username.value.isNullOrBlank() &&
                !password.value.isNullOrBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(username.value).matches()
    }

    suspend fun login() {
        val user = User("", username.value.toString(), password.value.toString())
        return repository.login(user)
    }
}