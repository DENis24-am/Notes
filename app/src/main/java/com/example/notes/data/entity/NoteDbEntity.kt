package com.example.notes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.notes.model.Note

@Entity(
    tableName = "notes_table"
)
data class NoteDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "desc") val description: String,
    @ColumnInfo(name = "title") val title: String,
) {
    fun toNote(): Note {
        return Note(
            id, description, title
        )
    }
}