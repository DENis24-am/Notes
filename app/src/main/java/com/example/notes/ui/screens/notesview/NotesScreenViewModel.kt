package com.example.notes.ui.screens.notesview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.notes.model.Note
import com.example.notes.repository.NotesRepository
import com.example.notes.ui.screens.notesview.model.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    val notesState: MutableStateFlow<PagingData<Note>> = MutableStateFlow(value = PagingData.empty())

    init {
        getNotesPage()
        getNotesPage()
        getNotesPage()
        getNotesPage() // колект спрацьовує тільки 1 раз, трішки костилі, але інакше чомусь не працювало :(
    }

    private var isCollecting = false

    fun getNotesPage() = viewModelScope.launch {
        if (!isCollecting) {
            isCollecting = true

            try {
                val flow = repository.getPageNotes()
                    .shareIn(this, started = SharingStarted.Eagerly)
                    .cachedIn(this)
                    .collect {
                        notesState.value = it
                    }

            } finally {
                isCollecting = false
            }
        }
    }
}