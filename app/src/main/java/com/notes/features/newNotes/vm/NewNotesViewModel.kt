package com.notes.features.newNotes.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.notes.data.NotesDatabase
import com.notes.data.NotesRepository
import com.notes.data.NotesTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewNotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesRepository
    var notesCategory = 0
    var notesDescription = ""

    init {
        val notesDao = NotesDatabase.getDatabase(application).notesDao()
        repository = NotesRepository(notesDao)
    }

    fun insert(notes: NotesTable) {
        viewModelScope.launch(Dispatchers.IO) { repository.insert(notes) }
    }
}

