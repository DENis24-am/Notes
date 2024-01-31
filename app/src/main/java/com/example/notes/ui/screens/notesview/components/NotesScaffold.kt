package com.example.notes.ui.screens.notesview.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.notes.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NotesScaffold(
    onNoteClick: (Note) -> Unit,
    onNewNoteClick: () -> Unit
) {
    var viewGrid by remember {
        mutableStateOf(false)
    }

    Scaffold(bottomBar = {
        BottomBar(
            viewGrid = viewGrid,
            createNewNote = {
                onNewNoteClick()
            },
            onViewGrid = {
                viewGrid = !viewGrid
                Log.e("view grid", viewGrid.toString())
            }
        )
    }) { paddingValue ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValue)
                .fillMaxSize()
        ) {
            NotesScreen(
                viewGrid = viewGrid,
                onItemClick = onNoteClick
            )
        }
    }
}