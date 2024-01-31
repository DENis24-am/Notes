package com.example.notes.di

import android.content.Context
import androidx.room.Room
import com.example.notes.data.dao.NotesDao
import com.example.notes.data.database.NoteDataBase
import com.example.notes.repository.NotesRepository
import com.example.notes.repository.NotesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotesModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = NoteDataBase::class.java,
            name = NoteDataBase.NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDataBase): NotesDao {
        return db.getDao()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepositoryImpl(notesDao)
    }
}