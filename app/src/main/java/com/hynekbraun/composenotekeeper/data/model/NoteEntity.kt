package com.hynekbraun.composenotekeeper.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hynekbraun.composenotekeeper.domain.model.NoteModel

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "header")
    val header: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "color")
    val color: Int
)

fun NoteEntity.toModel(): NoteModel {
    return NoteModel(
        id = id,
        header = header,
        content = content,
        date = date,
        color = color
    )
}
