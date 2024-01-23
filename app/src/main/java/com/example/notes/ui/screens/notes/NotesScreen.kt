package com.example.notes.ui.screens.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.ui.screens.notes.components.DeleteDialog
import com.example.notes.ui.screens.notes.components.NoteComponent

@Composable
fun NoteScreen(
    noteViewModel: NoteViewModel,
    onBackPress: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val state by noteViewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        NoteComponent(
            _title = state.data.title,
            _description = state.data.description,
            canDelete = state.canDelete,
            titleChange = noteViewModel::changeTitle,
            descriptionChange = noteViewModel::changeDesc,
            saveNote = {
                noteViewModel.saveOrUpdate()
                onBackPress()
            },
            onBackPress = onBackPress,
            onDeleteClick = {
                showDialog = !showDialog
            })
    }

    if (showDialog) {
        DeleteDialog(confirm = {
            noteViewModel.deleteNote()
            showDialog = !showDialog
            onBackPress()
        }, dismiss = {
            showDialog = !showDialog
        })
    }
}