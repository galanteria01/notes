package com.shanu.notes_app.ui.add_edit_note

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            .fillMaxSize()
            .padding(16.dp),
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
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = viewModel.title,
                placeholder = {
                    Text("Title")
                },
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.OnTitleChange(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.content,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.OnContentChange(it))
                },
                placeholder = {
                    Text("Content")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}