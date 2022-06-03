package com.shanu.notes_app.ui.app_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanu.notes_app.util.Routes
import com.shanu.notes_app.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AppInfoViewModel: ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AppInfoEvent) {
        when(event) {
            is AppInfoEvent.OnBackClicked -> {
                sendUiEvent(UiEvent.Navigate(Routes.NOTE_LIST))
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}