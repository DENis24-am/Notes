package com.example.notes.ui.screens.notes

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.dao.NotesDao
import com.example.notes.data.database.NoteDataBase
import com.example.notes.model.Note
import com.example.notes.repository.NotesRepository
import com.example.notes.ui.screens.notes.model.NotesEditState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NotesRepository) : ViewModel() {

    private val _state = MutableStateFlow(NotesEditState())
    val state = _state.asStateFlow()

    fun init(id: Long?) {
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
            Log.e("data", id.toString())
        }
    }

    fun changeTitle(title: String) {
        viewModelScope.launch {
            val temp = _state.value.data.description
            _state.update {
                it.copy(
                    data = Note(
                        0,
                        temp,
                        title
                    )
                )
            }
        }
    }

    fun changeDesc(desc: String) {
        viewModelScope.launch {
            val temp = _state.value.data.title
            _state.update {
                it.copy(
                    data = Note(
                        0,
                        desc,
                        temp
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
        Log.d("hello", "Hello")
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
}