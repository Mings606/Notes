package com.notes.features.home.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.notes.data.NotesDatabase
import com.notes.data.NotesRepository
import com.notes.data.NotesTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<NotesTable>>
    private val repository: NotesRepository

    init {
        val notesDao = NotesDatabase.getDatabase(application).notesDao()
        repository = NotesRepository(notesDao)
        readAllData = repository.allNotes
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteAll() }
    }
}

