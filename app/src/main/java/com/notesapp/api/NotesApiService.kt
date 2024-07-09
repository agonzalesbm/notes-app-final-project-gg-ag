package com.notesapp.api

import com.notesapp.models.Note
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface NotesApiService {
    @GET("/notes")
    suspend fun getNotes(): Response<ArrayList<Note>>

    @POST("/notes")
    suspend fun postNote(@Body note: Note): Call<Note>
}