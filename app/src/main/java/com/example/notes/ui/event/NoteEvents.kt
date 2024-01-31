package com.example.notes.ui.event

sealed class NoteEvents {
    object OnBackClick: NoteEvents()

    object SaveNoteClick: NoteEvents()

    object DeleteNoteClick: NoteEvents()
}