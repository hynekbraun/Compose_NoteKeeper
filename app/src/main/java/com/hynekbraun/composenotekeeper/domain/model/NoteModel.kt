package com.hynekbraun.composenotekeeper.domain.model

import androidx.compose.ui.graphics.toArgb
import com.hynekbraun.composenotekeeper.data.model.NoteEntity
import com.hynekbraun.composenotekeeper.ui.theme.*

data class NoteModel(
    val id: Int,
    val header: String,
    val content: String,
    val date: String,
    val color: Int = BackgroundYellow.toArgb()
) {
    companion object {
        val noteColors = listOf(
            BackgroundBlue,
            BackgroundCyan,
            BackgroundGreen,
            BackgroundYellow,
            BackgroundMagenta,
            BackgroundRed
        )
    }
}

fun NoteModel.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        header = header,
        content = content,
        date = date,
        color = color
    )
}