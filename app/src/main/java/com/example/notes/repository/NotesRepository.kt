package com.example.notes.repository

import androidx.paging.PagingData
import com.example.notes.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun insert(note: Note)

    suspend fun update(note: Note)

    suspend fun remove(note: Note)

    fun getAll(): Flow<List<Note>>

    fun getPageNotes(): Flow<PagingData<Note>>

    fun get(id: Long): Flow<Note?>
}