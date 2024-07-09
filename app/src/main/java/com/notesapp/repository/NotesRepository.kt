package com.notesapp.repository

import com.notesapp.api.NotesApiService
import com.notesapp.models.Note
import com.notesapp.room.NoteDao
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse

class NotesRepository(
    private val notesDao: NoteDao,
    private val notesApiService: NotesApiService,
) {
    val notes = notesDao.getAllNotes()

    suspend fun insert(note: Note) {
        notesDao.insertNote(note)
    }

    suspend fun update(note: Note) {
        notesDao.updateNote(note)
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
        var response = call.awaitResponse()
        if (response.isSuccessful) {
            getAll().collect() { }
        }
    }
}