package com.notesapp.viewmodels

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
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

    }
}