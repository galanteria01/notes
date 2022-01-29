package com.shanu.notes_app.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
        return dao.getNotes().map {
            it.filter {
                item -> item.title.contains(search)
            }
        }
    }

}