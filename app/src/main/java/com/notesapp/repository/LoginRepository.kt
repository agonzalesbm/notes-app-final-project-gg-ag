package com.notesapp.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.notesapp.api.NotesApiService
import com.notesapp.models.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.awaitResponse

const val DATASTORE_NAME = "USER"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class LoginRepository(
    private val notesApiService: NotesApiService,
    val context: Context,
) {

    private suspend fun saveUserId(userId: String) {
        context.dataStore.edit { user ->
            user[stringPreferencesKey("userId")] = userId
        }
    }

    suspend fun login(user: User): String? {
        val call = notesApiService.login(user)
        val response = call.awaitResponse()

        if (response.isSuccessful) {
            val responseBody = response.body()
            val gson = Gson()
            val jsonObject = gson.fromJson(responseBody, JsonObject::class.java)
            val userId = jsonObject.get("user_id").asString
            saveUserId(userId)
            return userId
        }
        return null
    }

    suspend fun getUserId(): String {
        return context.dataStore.data
            .map { user -> user[stringPreferencesKey("userId")] ?: "EMPTY" }
            .first()
    }
}