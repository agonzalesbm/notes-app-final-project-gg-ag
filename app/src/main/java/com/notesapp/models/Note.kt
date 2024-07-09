package com.notesapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("notes")
data class Note(
    @PrimaryKey
    @SerializedName("id")
    var id: String,
    @ColumnInfo
    @SerializedName("titulo")
    var title: String,
    @ColumnInfo
    @SerializedName("latitud")
    var latitude: Float,
    @ColumnInfo
    @SerializedName("longitud")
    var longitude: Float,
    @ColumnInfo
    @SerializedName("user_id")
    var userId: String,
    @ColumnInfo
    @SerializedName("fecha")
    var date: String,
    @ColumnInfo
    @SerializedName("body")
    var body: String
)
