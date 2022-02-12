package com.shanu.notes_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shanu.notes_app.ui.add_edit_note.AddEditNoteScreen
import com.shanu.notes_app.ui.note_list.NoteListScreen
import com.shanu.notes_app.ui.search_note.SearchNoteScreen
import com.shanu.notes_app.ui.theme.NotesappTheme
import com.shanu.notes_app.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NotesappTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.NOTE_LIST) {
                        composable(route = Routes.NOTE_LIST) {
                            NoteListScreen(
                                onNavigate = {
                                    navController.navigate(
                                        it.route
                                    )
                                }
                            )
                        }
                        composable(route = Routes.SEARCH_NOTE) {
                            SearchNoteScreen(
                                onNavigate = {
                                    navController.navigate(
                                        it.route
                                    )
                                }
                            )
                        }
                        composable(
                            route = Routes.ADD_EDIT_NOTE + "?noteId={noteId}",
                            arguments = listOf(
                                navArgument(name = "noteId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditNoteScreen(onPopBackStack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}