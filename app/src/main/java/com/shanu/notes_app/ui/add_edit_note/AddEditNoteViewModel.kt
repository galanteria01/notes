package com.shanu.notes_app.ui.add_edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanu.notes_app.data.Note
import com.shanu.notes_app.data.NoteRepository
import com.shanu.notes_app.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var note by mutableStateOf<Note?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val noteId = savedStateHandle.get<Int>("noteId")
        if(noteId != -1) {
            viewModelScope.launch {
                if (noteId != null) {
                    repository.getNoteById(noteId)?.let {note ->
                        title = note.title
                        content = note.content ?: ""
                        this@AddEditNoteViewModel.note = note
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditNoteEvent.OnContentChange -> {
                content = event.content
            }
            is AddEditNoteEvent.OnSaveNoteClick -> {
                viewModelScope.launch {
                    if(title.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The title can't be empty"
                        ))
                        return@launch
                    }
                    repository.insertNote(
                        Note(
                            title = title,
                            content = content,
                            isDone = note?.isDone ?: false,
                            id = note?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}