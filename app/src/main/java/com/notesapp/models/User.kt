package com.notesapp.models

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String,
)
