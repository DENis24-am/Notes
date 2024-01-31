package com.example.notes.ui.screens.notesview.model

import com.example.notes.model.Note

data class NotesState(
    val isLoading: Boolean = false,
    val data: List<Note>? = null,
    val errorMessage: String? = null
)