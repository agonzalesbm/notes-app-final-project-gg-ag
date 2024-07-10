package com.notesapp.api

import com.notesapp.models.Note
import com.notesapp.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT

interface NotesApiService {
    @GET("/notes?user_id=usuario6")
    suspend fun getNotes(): Response<ArrayList<Note>>

    @POST("/notes")
    fun postNote(@Body note: Note): Call<String>

    @PUT("/notes")
    fun updateNote(@Body note: Note): Call<String>

    @POST("/login")
    fun login(@Body user: User): Call<String>
}