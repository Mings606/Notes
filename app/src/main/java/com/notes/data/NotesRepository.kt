package com.notes.data

import androidx.lifecycle.LiveData
import com.notes.dao.NotesDao

class NotesRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<NotesTable>> = notesDao.getAllNotes()

    suspend fun insert(notes: NotesTable) {
        notesDao.insertNotes(notes)
    }

    suspend fun deleteAll() {
        notesDao.deleteAllNotes()
    }
}
