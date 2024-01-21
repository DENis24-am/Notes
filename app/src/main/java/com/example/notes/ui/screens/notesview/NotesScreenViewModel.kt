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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
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
    }

    fun getNotesPage() {
        viewModelScope.launch {
            repository.getPageNotes().debounce(500).cachedIn(viewModelScope).collect {
                notesState.value = it
            }
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                _state.update { state ->
                    state.copy(
                        data = list
                    )
                }
            }
        }
    }
}