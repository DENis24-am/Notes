package com.example.notes.ui.screens.notesview.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.model.Note
import com.example.notes.ui.screens.common.components.Toast
import com.example.notes.ui.screens.notesview.NotesScreenViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.notes.ui.screens.notesview.model.NotesState

@Composable
fun NotesScreen(
    viewGrid: Boolean = false,
    viewModel: NotesScreenViewModel = hiltViewModel(),
    onItemClick: (Note) -> Unit
) {
    val state by viewModel.state.collectAsState()

    val notes = viewModel.notes.collectAsLazyPagingItems()

    if (state.isLoading) {
        DefaultContent()
    } else if (notes.itemCount > 0) {
        NotesList(
            viewGrid = viewGrid,
            notes = notes,
            onItemClick = onItemClick
        )
    } else {
        DefaultContent()
    }
}