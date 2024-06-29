package com.notesapp.repository

import com.notesapp.models.Note
import com.notesapp.room.NoteDao

class NotesRepository(private val notesDao: NoteDao) {
    val notes = notesDao.getAllNotes()

    suspend fun insert(note: Note) {
        notesDao.insertNote(note)
    }

    suspend fun update(note: Note) {
        notesDao.updateNote(note)
    }
}