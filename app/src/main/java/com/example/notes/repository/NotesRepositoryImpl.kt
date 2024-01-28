package com.example.notes.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.notes.data.dao.NotesDao
import com.example.notes.data.entity.NoteDbEntity
import com.example.notes.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val dao: NotesDao
): NotesRepository {

    override suspend fun insert(note: Note) {
        dao.add(NoteDbEntity(
            id = note._id,
            description = note.description,
            title = note.title
        ))
    }

    override suspend fun update(note: Note) {
        dao.update(
            NoteDbEntity(
                id = note._id,
                description = note.description,
                title = note.title
            )
        )

        Log.e("update1", note.toString())
    }

    override suspend fun remove(note: Note) {
        dao.remove(
            NoteDbEntity(
                id = note._id,
                description = note.description,
                title = note.title
            )
        )
    }

    override fun getAll(): Flow<List<Note>> {
        return dao.getAll().map { list -> list.map { it.toNote() } }
    }

    override fun getPageNotes(): Flow<PagingData<Note>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = dao::getAllPagin
        ).flow.map {
            it.map { it.toNote() }
        }
    }

    override fun get(id: Long): Flow<Note?> {
        return dao.get(id).map { it?.toNote() }
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}