package com.notesapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.notesapp.models.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note: Note): String

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>
}