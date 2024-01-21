package com.example.notes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.data.dao.NotesDao
import com.example.notes.data.entity.NoteDbEntity

@Database(
    version = 1,
    entities = [
        NoteDbEntity::class
    ],
    exportSchema = false
)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun getDao(): NotesDao

    companion object {
        const val NAME = "database"
    }
}