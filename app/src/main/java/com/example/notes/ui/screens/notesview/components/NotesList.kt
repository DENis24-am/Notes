package com.example.notes.ui.screens.notesview.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.notes.model.Note

@Composable
fun NotesList(
    viewGrid: Boolean = false,
    notes: List<Note>,
    onItemClick: (Note) -> Unit
) {
    AnimatedContent(targetState = viewGrid, label = "") {viewGridAnimate ->
        LazyVerticalGrid(
            modifier = Modifier,
            columns = GridCells.Adaptive(minSize = if (viewGridAnimate) 100.dp else 1000.dp),
            contentPadding = PaddingValues(top = 10.dp, end = 10.dp, start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                notes.size,
                key = {
                    notes[it]?._id ?: it
                }
            ) {
                val note = notes[it] ?: Note(0, "", "")
                NoteCard(note) { clickedNote ->
                    onItemClick(clickedNote)
                }
            }
        }
    }
}