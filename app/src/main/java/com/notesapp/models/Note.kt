package com.notesapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class Note(
    @PrimaryKey
    var id: String,
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var latitude: Float,
    @ColumnInfo
    var longitude: Float,
    @ColumnInfo
    var userId: Long,
    @ColumnInfo
    var date: String,
    @ColumnInfo
    var body: String
)
