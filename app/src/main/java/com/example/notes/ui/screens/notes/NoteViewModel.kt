package com.example.notes.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.model.Note
import com.example.notes.repository.NotesRepository
import com.example.notes.ui.event.NoteEvents
import com.example.notes.ui.screens.notes.model.NotesEditState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel @AssistedInject constructor(
    @Assisted private val id: Long?,
    private val repository: NotesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotesEditState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<NoteEvents>()
    val event = _event.asSharedFlow()

    init {
        initialNote()
    }

    private fun initialNote() {
        if(id != null) {
            viewModelScope.launch {
                repository.get(id).collect { note ->
                    _state.update {
                        it.copy(
                            data = note ?: Note(0, "", ""),
                            canDelete = true
                        )
                    }
                }
            }
        }
    }

    fun changeTitle(title: String) {
        viewModelScope.launch {
            val temp = _state.value.data.description
            val id = _state.value.data.id
            _state.update {
                it.copy(
                    data = Note(
                        id = id,
                        description = temp,
                        title = title
                    )
                )
            }
        }
    }

    fun changeDescription(desc: String) {
        viewModelScope.launch {
            val temp = _state.value.data.title
            val id = _state.value.data.id
            _state.update {
                it.copy(
                    data = Note(
                        id = id,
                        description = desc,
                        title = temp
                    )
                )
            }
        }
    }

    private fun createNote() {
        viewModelScope.launch {
            repository.insert(_state.value.data)
        }
    }

    private fun updateNote() {
        viewModelScope.launch {
            repository.update(_state.value.data)
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            repository.remove(_state.value.data)
        }
    }

    fun saveOrUpdate() {
        if(state.value.canDelete) {
            updateNote()
        } else {
            createNote()
        }
    }

    fun onSaveClick() {
        saveOrUpdate()

        viewModelScope.launch {
            _event.emit(NoteEvents.SaveNoteClick)
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _event.emit(NoteEvents.OnBackClick)
        }
    }

    fun onDeleteClick() {
        deleteNote()

        viewModelScope.launch {
            _event.emit(NoteEvents.DeleteNoteClick)
        }
    }
    @AssistedFactory
    interface Factory {
        fun create(id: Long?): NoteViewModel
    }
}