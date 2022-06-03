package com.shanu.notes_app.data.repository

import com.shanu.notes_app.data.database.NoteDao
import com.shanu.notes_app.data.models.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val dao: NoteDao): NoteRepository {

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override fun searchNotes(search: String): Flow<List<Note>> {
        return dao.searchNotes(search)
    }
}
