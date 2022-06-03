package com.shanu.notes_app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shanu.notes_app.data.database.NoteDao
import com.shanu.notes_app.data.models.Note

@Database(
    entities = [Note::class],
    version = 1
)

abstract class NoteDatabase: RoomDatabase() {
    abstract val dao: NoteDao
}