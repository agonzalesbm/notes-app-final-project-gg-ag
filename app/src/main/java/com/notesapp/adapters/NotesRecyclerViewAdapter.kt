package com.notesapp.adapters

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.notesapp.R
import com.notesapp.databinding.NoteCardBinding
import com.notesapp.models.Note

class NotesRecyclerViewAdapter(
    var notes: List<Note>,
    val clickListener: (Note) -> Unit,
    val longClickListener: (View, Note) -> Unit
):RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(val binding: NoteCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteCardBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.note = note
        holder.binding.contactCell.setOnClickListener {
            clickListener(note)
        }
        holder.binding.contactCell.setOnLongClickListener {
            longClickListener(it, note)
            true
        }
    }
}