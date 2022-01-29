package com.shanu.notes_app.ui.add_edit_note

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shanu.notes_app.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditNoteScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold (
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar (
                title = {
                    androidx.compose.material3.Text(
                        "Edit Note",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(AddEditNoteEvent.OnBackClick) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.OnSaveNoteClick)
                          },
                text = {
                    Text("Save")
                       },
                icon = {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.title,
                label = {
                    Text("Title")
                },
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.OnTitleChange(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.content,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.OnContentChange(it))
                },
                label = {
                    Text("Content")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 60.dp),
                singleLine = false,
                maxLines = 5,

            )
        }
    }
}