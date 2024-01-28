package com.example.notes.ui.nav

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notes.ui.screens.notes.NoteScreen
import com.example.notes.ui.screens.notes.NoteViewModel
import com.example.notes.ui.screens.notesview.components.NotesHomeScreen

@Composable
fun RootNavigationGraph(
    factory: NoteViewModel.Factory,
    navHostController: NavHostController,
    context: Context
) {

    NavHost(
        navController = navHostController,
        startDestination = "home"
    ) {
        composable("home") {
            NotesHomeScreen(
                onNoteClick = {
                    navHostController.navigate("add_note/${it._id}")
                    Log.d("note click", "${it._id}")
                },
                onNewNoteClick = {
                    navHostController.navigate("add_note/${0L}")
                    Log.d("note click", "${0}")
                }
            )
        }
        composable("add_note/{note}") {
            Log.e("error", "${it.arguments?.getString("note")}")
            val noteId = it.arguments?.getString("note")?.toLongOrNull()

            val noteViewModel: NoteViewModel = factory.create(noteId)

            NoteScreen(noteViewModel) {
                navHostController.navigate("home")
            }
        }
    }
}