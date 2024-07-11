package com.notesapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.notesapp.viewmodels.LoginViewModel
import com.notesapp.viewmodels.NoteDetailsViewModel
import com.notesapp.viewmodels.NoteDetailsViewModelFactory
import com.notesapp.viewmodels.NotesSharedViewModel
import com.notesapp.viewmodels.NotesViewModelFactory
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.notesapp.datastore.user
import com.notesapp.viewmodels.LoginShareViewModelFactory

//val Context.dataStore by preferencesDataStore(name = "USER_PREFERENCES")
class MainActivity : AppCompatActivity() {
    lateinit var notesViewModel: NotesSharedViewModel
    lateinit var notesDetailsViewModel: NoteDetailsViewModel
    lateinit var notesLoginViewModel: LoginViewModel
    lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dataStore = user
        val factory = NotesViewModelFactory(applicationContext)
        notesViewModel = ViewModelProvider(this, factory).get(NotesSharedViewModel::class.java)

        val detailsFactory = NoteDetailsViewModelFactory(notesViewModel)
        notesDetailsViewModel = ViewModelProvider(this, detailsFactory).get(NoteDetailsViewModel::class.java)

        val loginFactory = LoginShareViewModelFactory(applicationContext)
        notesLoginViewModel = ViewModelProvider(this, loginFactory).get(LoginViewModel::class.java)


    }

}