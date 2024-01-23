package com.example.notes.ui.screens.notesview.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.model.Note
import com.example.notes.ui.screens.common.components.Toast
import com.example.notes.ui.screens.notesview.NotesScreenViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.notes.data.PaginationState
import com.example.notes.ui.screens.notesview.model.NotesState

@Composable
fun NotesScreen(
    viewGrid: Boolean = false,
    viewModel: NotesScreenViewModel = hiltViewModel(),
    onItemClick: (Note) -> Unit
) {
//    val state by viewModel.state.collectAsState()

    val lazyColumnListState = rememberLazyListState()

//    val notes = viewModel.notesState.collectAsLazyPagingItems()

    val notes by viewModel.notesList.collectAsState()
    val pagingState by viewModel.pagingState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.clearPaging()
        viewModel.getNotes()
    }

    val shouldPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate
                    && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -10) >=
                    (lazyColumnListState.layoutInfo.totalItemsCount-3)
        }
    }

    LaunchedEffect(shouldPaginate.value) {
        if (shouldPaginate.value && pagingState == PaginationState.REQUEST_INACTIVE) {
            viewModel.getNotes()
        }
    }

    Log.e("TAG", notes.toString())

    LazyColumn(
        state = lazyColumnListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            notes.size,
            key = { notes[it]._id },
        ) {

            NoteCard(
                note = notes[it],
                onItemClick = onItemClick
            )
        }
    }

//    if (state.isLoading) {
//        DefaultContent()
//    } else if (notes.isNotEmpty()) {
//        Log.e("GET", notes[0].toString())
//        NotesList(
//            viewGrid = viewGrid,
//            notes = notes,
//            onItemClick = onItemClick
//        )
//    } else {
//        DefaultContent()
//    }
}