package com.example.notes.ui.nav

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    navHostController.navigate("add_note/${it.id}")
                    Log.d("note click", "${it.id}")
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

            val noteViewModel = injectViewModel {
                factory.create(noteId)
            }

            NoteScreen(noteViewModel) {
                navHostController.navigate("home")
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> injectViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: () -> T,
): T = viewModel(
    modelClass = T::class.java,
    key = key,
    factory = object : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModelInstanceCreator() as T
        }
    }
)