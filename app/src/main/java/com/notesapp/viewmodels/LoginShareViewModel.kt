package com.notesapp.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.models.User
import com.notesapp.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginShareViewModel(val repository: LoginRepository) :
    ViewModel() {
    fun login(user: User) = viewModelScope.launch {
        val response = repository.login(user)
        return response.body
        //save the data on dataStore using the response that containts the body
//        dataStore.edit { preferences ->
//            preferences[stringPreferencesKey("userName")] = response.toString()
//        }
//        dataStore.data.collect {preferences ->
//            val user = preferences[stringPreferencesKey("userName")] ?: "userNamePrinted"
//            println(user)
//        }
    }
}