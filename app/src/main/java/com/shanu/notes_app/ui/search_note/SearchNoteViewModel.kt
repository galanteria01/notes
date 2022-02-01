package com.shanu.notes_app.ui.search_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanu.notes_app.data.NoteRepository
import com.shanu.notes_app.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    var search by mutableStateOf("")
        private set

    val notes = repository.searchNotes(search)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchNoteEvent) {
        when(event) {
            is SearchNoteEvent.OnSearchNote -> {
                search = event.search
            }
            is SearchNoteEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}