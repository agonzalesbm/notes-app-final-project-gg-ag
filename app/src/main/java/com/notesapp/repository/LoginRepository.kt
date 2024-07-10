package com.notesapp.repository

import com.notesapp.api.NotesApiService
import com.notesapp.models.User
import retrofit2.awaitResponse

class LoginRepository(
    private val notesApiService: NotesApiService,
    ) {

    suspend fun login(user: User): String? {
        val call = notesApiService.login(user)
        val response = call.awaitResponse()
        if (response.isSuccessful) {
            return response.body()
        }

        return null
    }
}