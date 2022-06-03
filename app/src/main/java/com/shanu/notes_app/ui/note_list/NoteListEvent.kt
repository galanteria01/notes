package com.shanu.notes_app.ui.note_list

import com.shanu.notes_app.data.models.Note

sealed class NoteListEvent {
    data class OnDeleteNoteClick(val note: Note): NoteListEvent()
    data class OnDoneChange(val note: Note, val isDone: Boolean): NoteListEvent()
    data class OnNoteClick(val note: Note): NoteListEvent()
    object OnUndoDeleteClick: NoteListEvent()
    object OnAddNoteClick: NoteListEvent()
    object OnSearchClicked: NoteListEvent()
    object OnEditClicked: NoteListEvent()
    object OnSelectAllClicked: NoteListEvent()
    object OnInfoClicked: NoteListEvent()
}