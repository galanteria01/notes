package com.shanu.notes_app.ui.search_note

import androidx.compose.material.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNoteScreen(
    viewModel: SearchNoteViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TextField(value = viewModel.search, onValueChange = {})
        }
    ) {

    }
}