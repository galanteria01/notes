package com.shanu.notes_app.data.repository

import com.shanu.notes_app.data.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun getNoteById(id: Int): Note?

    fun getNotes(): Flow<List<Note>>

    fun searchNotes(search: String): Flow<List<Note>>

}