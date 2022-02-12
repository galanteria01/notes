package com.shanu.notes_app.ui.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanu.notes_app.data.Note
import com.shanu.notes_app.data.NoteRepository
import com.shanu.notes_app.util.Routes
import com.shanu.notes_app.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    val notes = repository.getNotes()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedNote: Note ?= null

    fun onEvent(event: NoteListEvent) {
        when(event) {
            is NoteListEvent.OnNoteClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_NOTE + "?noteId=${event.note.id}"))
            }
            is NoteListEvent.OnAddNoteClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_NOTE))
            }
            is NoteListEvent.OnUndoDeleteClick -> {
                deletedNote?.let { note ->
                    viewModelScope.launch {
                        repository.insertNote(note)
                    }
                }
            }
            is NoteListEvent.OnDeleteNoteClick -> {
                viewModelScope.launch {
                    deletedNote = event.note
                    repository.deleteNote(event.note)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Todo deleted",
                        action = "Undo"
                    ))
                }
            }
            is NoteListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertNote(
                        event.note.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
            is NoteListEvent.OnSearchClicked -> {
                sendUiEvent(UiEvent.Navigate(Routes.SEARCH_NOTE))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}