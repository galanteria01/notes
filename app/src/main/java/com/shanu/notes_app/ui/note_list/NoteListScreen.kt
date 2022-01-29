package com.shanu.notes_app.ui.note_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shanu.notes_app.R
import com.shanu.notes_app.util.UiEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect


@Composable
fun NoteListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val notes = viewModel.notes.collectAsState(initial = emptyList())
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
    
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NoteListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    LaunchedEffect(isRefreshing){
        if(isRefreshing){
            delay(1000L)
            isRefreshing = false
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ExtendedFloatingActionButton(text = {
                Text("Add Note")
            },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                },
                onClick = {
                viewModel.onEvent(NoteListEvent.OnAddNoteClick)
            })
        },
        topBar = {
            CenterAlignedTopAppBar (
                title = {
                    Text(
                    "Notes",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                        },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                }
                    )
        }
    ) {
        if(notes.value.isNotEmpty()) {
            SwipeRefresh(state = swipeRefreshState, onRefresh = { isRefreshing = true }) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                )
                {
                    items(notes.value) { note ->
                        NoteItem(
                            note = note,
                            onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(NoteListEvent.OnNoteClick(note))
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
                    modifier = Modifier.size(400.dp).offset(y = 100.dp)
                )
            }
        }
    }
