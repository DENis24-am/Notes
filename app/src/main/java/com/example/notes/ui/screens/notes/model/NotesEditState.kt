package com.example.notes.ui.screens.notes.model

import com.example.notes.model.Note
import kotlinx.coroutines.flow.update

data class NotesEditState(
    val canDelete: Boolean = false,
    val data: Note = Note(
                id = 0,
                description = "",
                title = ""
            )
)
