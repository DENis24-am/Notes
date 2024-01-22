package com.example.notes.ui.screens.notesview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.PaginationState
import com.example.notes.model.Note
import com.example.notes.repository.NotesRepository
import com.example.notes.ui.screens.notesview.model.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

//    val notesState: MutableStateFlow<PagingData<Note>> = MutableStateFlow(value = PagingData.empty())

    init {
        //getNotesPage()
    }

//    fun getNotesPage() {
//        viewModelScope.launch { //ограничить количество раз до 1
//            repository.getPageNotes().debounce(500).cachedIn(viewModelScope)
//                .collect {
//                    notesState.value = it
//                }
//        }
//    }

    private val _notesList = MutableStateFlow<MutableList<Note>>(mutableListOf())
    val notesList: StateFlow<List<Note>> = _notesList.asStateFlow()

    private val _pagingState = MutableStateFlow(PaginationState.LOADING)
    val pagingState: StateFlow<PaginationState> = _pagingState.asStateFlow()

    private var page = INITIAL_PAGE
    var canPaginate by mutableStateOf(false)

    fun getNotes() {
        if (page == INITIAL_PAGE || (page != INITIAL_PAGE && canPaginate) && _pagingState.value == PaginationState.REQUEST_INACTIVE) {
            _pagingState.update { if (page == INITIAL_PAGE) PaginationState.LOADING else PaginationState.PAGINATING }
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getPagingNotes(PAGE_SIZE, page * PAGE_SIZE)

                canPaginate = result.size == PAGE_SIZE

                if (page == INITIAL_PAGE) {
                    if (result.isEmpty()) {
                        _pagingState.update { PaginationState.EMPTY }
                        return@launch
                    }
                    _notesList.value.clear()
                    _notesList.value.addAll(result)
                } else {
                    _notesList.value.addAll(result)
                }

                _pagingState.update { PaginationState.REQUEST_INACTIVE }

                if (canPaginate) {
                    page++
                }

                if (!canPaginate) {
                    _pagingState.update { PaginationState.PAGINATION_EXHAUST }
                }
            } catch (e: Exception) {
                _pagingState.update { if (page == INITIAL_PAGE) PaginationState.ERROR else PaginationState.PAGINATION_EXHAUST }
            }
        }
    }

    fun clearPaging() {
        page = INITIAL_PAGE
        _pagingState.update { PaginationState.LOADING }
        canPaginate = false
    }

    companion object {
        const val PAGE_SIZE = 10
        const val INITIAL_PAGE = 0
    }
}