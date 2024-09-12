package com.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NotesTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val categoryId: Int,
    val notesDescription: String,
    val createAt: Long
)
