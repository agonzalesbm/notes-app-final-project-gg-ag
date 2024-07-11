package com.notesapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.user: DataStore<Preferences> by preferencesDataStore(name = "userInfo")
class DataStoreManager {
}

