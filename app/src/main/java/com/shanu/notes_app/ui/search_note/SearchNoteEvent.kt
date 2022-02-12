package com.shanu.notes_app.ui.search_note

import com.shanu.notes_app.data.Note
import com.shanu.notes_app.ui.note_list.NoteListEvent

sealed class SearchNoteEvent {
    data class OnDeleteNoteClick(val note: Note): SearchNoteEvent()
    data class OnSearchNote(val search: String): SearchNoteEvent()
    data class OnNoteClick(val note: Note): SearchNoteEvent()
    data class OnSearchValueChanged(val search: String): SearchNoteEvent()
    data class OnDoneChange(val note: Note, val isDone: Boolean): SearchNoteEvent()
    object OnBackClick: SearchNoteEvent()
    object OnUndoDeleteClick: SearchNoteEvent()
}