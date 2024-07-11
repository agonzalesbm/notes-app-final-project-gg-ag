package com.notesapp.viewmodels

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.models.User
import com.notesapp.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel (val loginRepository: LoginRepository): ViewModel() {
    var isValid = MediatorLiveData<Boolean>()
    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()

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

    fun login() {
        viewModelScope.launch {
            val userName =username.value.toString()
            val password =password.value.toString()
            loginRepository.login(User(userName, password ))
        }

    }
}