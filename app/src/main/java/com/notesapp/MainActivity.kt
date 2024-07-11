package com.notesapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.notesapp.viewmodels.LoginViewModel
import com.notesapp.viewmodels.LoginViewModelFactory
import com.notesapp.viewmodels.NoteDetailsViewModel
import com.notesapp.viewmodels.NoteDetailsViewModelFactory
import com.notesapp.viewmodels.NotesSharedViewModel
import com.notesapp.viewmodels.NotesViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var notesViewModel: NotesSharedViewModel
    lateinit var notesDetailsViewModel: NoteDetailsViewModel
    lateinit var notesLoginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val factory = NotesViewModelFactory(applicationContext)
        notesViewModel = ViewModelProvider(this, factory).get(NotesSharedViewModel::class.java)
        val detailsFactory = NoteDetailsViewModelFactory(notesViewModel)
        notesDetailsViewModel = ViewModelProvider(this, detailsFactory).get(NoteDetailsViewModel::class.java)

        val loginViewModelFactory = LoginViewModelFactory(applicationContext)
        notesLoginViewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
    }
}