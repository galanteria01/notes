package com.shanu.notes_app.ui.search_note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shanu.notes_app.R
import com.shanu.notes_app.util.UiEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNoteScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: SearchNoteViewModel = hiltViewModel()
) {
    val notes = viewModel.filtered.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val isPlaying by remember {
        mutableStateOf(true)
    }
    val speed by remember {
        mutableStateOf(1f)
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.empty)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false
    )

    LaunchedEffect(isRefreshing){
        if(isRefreshing){
            delay(1000L)
            isRefreshing = false
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(SearchNoteEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            OutlinedTextField(
                value = viewModel.search,
                onValueChange = {
                    viewModel.onEvent(SearchNoteEvent.OnSearchNote(it))
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                singleLine = true
            )
        },
        scaffoldState = scaffoldState
    ) {
        if(notes.value.isNotEmpty()) {
            SwipeRefresh(state = swipeRefreshState, onRefresh = { isRefreshing = true }) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                )
                {
                    items(notes.value) { note ->
                        SearchNoteItem(
                            note = note,
                            onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(SearchNoteEvent.OnNoteClick(note))
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        } else {
            LottieAnimation(
                composition,
                progress,
                modifier = Modifier
                    .size(400.dp)
                    .offset(y = 100.dp)
            )
        }
    }
}