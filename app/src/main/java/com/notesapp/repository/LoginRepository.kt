package com.notesapp.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.notesapp.api.NotesApiService
import com.notesapp.models.User
import retrofit2.awaitResponse


const val DATASTORE_NAME = "USER"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class LoginRepository(
    private val notesApiService: NotesApiService,
    val context: Context,
) {

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { user ->
            user[stringPreferencesKey("userId")] = userId
        }
    }

    suspend fun login(user: User): String? {
        val call = notesApiService.login(user)
        val response = call.awaitResponse()
        println(response)
        if (response.isSuccessful) {
            val userId: String = response.body().toString()
            saveUserId(userId)
            println(userId)
        }
        return null
    }
}