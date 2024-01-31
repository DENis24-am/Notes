package com.example.notes.ui.screens.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.notes.ui.event.NoteEvents
import com.example.notes.ui.screens.notes.components.DeleteDialog
import com.example.notes.ui.screens.notes.components.NoteComponent

@Composable
fun NoteScreen(
    noteViewModel: NoteViewModel,
    onBackPress: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val state by noteViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        noteViewModel.event.collect {
            when(it) {
                NoteEvents.SaveNoteClick -> onBackPress()
                NoteEvents.OnBackClick -> onBackPress()
                NoteEvents.DeleteNoteClick -> onBackPress()
                else -> return@collect
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        NoteComponent(
            _title = state.data.title,
            _description = state.data.description,
            canDelete = state.canDelete,
            titleChange = noteViewModel::changeTitle,
            descriptionChange = noteViewModel::changeDescription,
            saveNote = noteViewModel::onSaveClick,
            onBackPress = onBackPress,
            onDeleteClick = {
                showDialog = !showDialog
            })
    }

    if (showDialog) {
        DeleteDialog(confirm = noteViewModel::onDeleteClick,
            dismiss = {
                showDialog = !showDialog
            }
        )
    }
}