package com.shanu.notes_app.ui.app_info

sealed class AppInfoEvent {
    object OnBackClicked: AppInfoEvent()
}