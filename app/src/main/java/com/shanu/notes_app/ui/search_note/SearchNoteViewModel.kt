package com.shanu.notes_app.ui.search_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanu.notes_app.data.Note
import com.shanu.notes_app.data.NoteRepository
import com.shanu.notes_app.ui.note_list.NoteListEvent
import com.shanu.notes_app.util.Routes
import com.shanu.notes_app.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    var search by mutableStateOf("")
        private set

    var notes = repository.getNotes()
    var filtered = notes

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedNote: Note?= null

    fun onEvent(event: SearchNoteEvent) {
        when (event) {
            is SearchNoteEvent.OnNoteClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_NOTE + "?noteId=${event.note.id}"))
            }
            is SearchNoteEvent.OnUndoDeleteClick -> {
                deletedNote?.let { note ->
                    viewModelScope.launch {
                        repository.insertNote(note)
                    }
                }
            }
            is SearchNoteEvent.OnSearchNote -> {
                viewModelScope.launch {
                    search = event.search
                    filtered = notes.map {
                        it.filter { item ->
                            item.title.lowercase().contains(search.lowercase())
                        }
                    }
                }
            }
            is SearchNoteEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is SearchNoteEvent.OnDeleteNoteClick -> {
                viewModelScope.launch {
                    deletedNote = event.note
                    repository.deleteNote(event.note)
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