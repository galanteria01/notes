package com.shanu.notes_app.ui.note_list

import com.shanu.notes_app.data.Note

sealed class NoteListEvent {
    data class OnDeleteNoteClick(val note: Note): NoteListEvent()
    data class OnDoneChange(val note: Note, val isDone: Boolean): NoteListEvent()
    object OnUndoDeleteClick: NoteListEvent()
    data class OnNoteClick(val note: Note): NoteListEvent()
    object OnAddNoteClick: NoteListEvent()
}