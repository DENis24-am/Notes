package com.example.notes.ui.nav

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.notes.ui.screens.notes.NoteScreen
import com.example.notes.ui.screens.notes.NoteViewModel
import com.example.notes.ui.screens.notesview.components.NotesHomeScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgument
import com.example.notes.model.Note

@Composable
fun RootNavigationGraph(navHostController: NavHostController, context: Context) {
    NavHost(
        navController = navHostController,
        startDestination = "home"
    ) {
        composable("home") {
            NotesHomeScreen(onNoteClick = {
                navHostController.navigate("add_note/${it._id}")
                Log.d("note click", "${it._id}")
            }) {
                navHostController.navigate("add_note/${0L}")
                Log.d("note click", "${0}")
            }
        }
        composable("add_note/{note}") {
            val noteViewModel: NoteViewModel = hiltViewModel()
            Log.e("error", "${it.arguments?.getString("note")}")
            val noteId = it.arguments?.getString("note")?.toLongOrNull() ?: 0L

            if (noteId > 0L)
                noteViewModel.init(noteId)

            NoteScreen(noteViewModel) {
                navHostController.navigate("home")
            }
        }
    }
}