package com.notesapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.notesapp.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesAppDataBase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        @Volatile
        private var INSTACE: NotesAppDataBase? = null
        fun getInstance(context: Context): NotesAppDataBase {
            synchronized(this) {
                var instance = INSTACE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, NotesAppDataBase::class.java, "notes_db"
                    ).fallbackToDestructiveMigration().build()
                }
                INSTACE = instance
                return instance
            }
        }
    }
}