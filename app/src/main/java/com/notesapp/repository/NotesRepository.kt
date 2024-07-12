package com.notesapp.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.notesapp.api.NotesApiService
import com.notesapp.models.Note
import com.notesapp.room.NoteDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.awaitResponse

class NotesRepository(
    private val notesDao: NoteDao,
    private val notesApiService: NotesApiService,
    private val context: Context
) {
    val notes = notesDao.getAllNotes()

    suspend fun insert(note: Note) {
        notesDao.insertNote(note)
    }

    suspend fun update(note: Note) {
        notesDao.updateNote(note)
    }

    suspend fun delete(note: Note) {
        notesDao.deleteNote(note)
    }

    fun getAll() = flow {
        val response = notesApiService.getNotes()
        if (response.isSuccessful && response.body() != null) {
            notesDao.insertAll(response.body()!!)
            emit(true)
        } else {
            emit(false)
        }
    }

    suspend fun insertToApi(note: Note) {
        val call = notesApiService.postNote(note)
        val response = call.awaitResponse()
        if (response.isSuccessful) {
            getAll().collect { }
        }
    }

    suspend fun updateToApi(note: Note) {
        val call = notesApiService.updateNote(note)
        val response = call.awaitResponse()
        if (response.isSuccessful) {
            getAll().collect { }
        }
    }

    suspend fun deleteToApi(note: Note) {
        delete(note)
        val call = notesApiService.deleteNote(note)
        val response = call.awaitResponse()
        if (response.isSuccessful) {
            getAll().collect { }
        }
    }

    suspend fun getUserId(): String {
        return context.dataStore.data
            .map { user -> user[stringPreferencesKey("userId")] ?: "EMPTY" }
            .first()
    }

    suspend fun deleteSession() {
        context.dataStore.edit { user ->
            user[stringPreferencesKey("userId")] = "EMPTY"
        }
    }
}