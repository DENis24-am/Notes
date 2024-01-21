package com.example.notes.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.notes.data.NotesLoader
import com.example.notes.data.NotesPagingSource
import com.example.notes.data.dao.NotesDao
import com.example.notes.data.entity.NoteDbEntity
import com.example.notes.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

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

    override suspend fun getNotes(pageIndex: Int, pageSize: Int): List<Note>
    = withContext(Dispatchers.IO) {
        delay(1000L) //для виду

        val offset = pageIndex * pageSize

        val list = dao.getAllPagin(pageSize, offset)

        return@withContext list
            .map { it.toNote() }
    }

    override fun getPageNotes(): Flow<PagingData<Note>> {
        val loader: NotesLoader = { index, size ->
            getNotes(index, size)
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NotesPagingSource(loader, PAGE_SIZE)
            }
        ).flow
    }

    override fun get(id: Long): Flow<Note?> {
        return dao.get(id).map { it?.toNote() }
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}