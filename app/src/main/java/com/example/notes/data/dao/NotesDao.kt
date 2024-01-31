package com.example.notes.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notes.data.entity.NoteDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table")
    fun getAll(): Flow<List<NoteDbEntity>>

    @Query(
        "SELECT * FROM notes_table"
    )

    fun getAllPagin(): PagingSource<Int, NoteDbEntity>
    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun get(id: Long): Flow<NoteDbEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(note: NoteDbEntity)

    @Update//(entity = NoteDbEntity::class, I)
    suspend fun update(note: NoteDbEntity)

    @Delete
    suspend fun remove(note: NoteDbEntity)
}