package com.shanu.notes_app.ui.search_note

sealed class SearchNoteEvent {
    data class OnSearchNote(val search: String): SearchNoteEvent()
    object OnBackClick: SearchNoteEvent()
}