package com.shanu.notes_app.ui.add_edit_note

sealed class AddEditNoteEvent {
    data class OnTitleChange(val title: String): AddEditNoteEvent()
    data class OnContentChange(val content: String): AddEditNoteEvent()
    object OnSaveNoteClick: AddEditNoteEvent()
    object OnBackClick: AddEditNoteEvent()
}
