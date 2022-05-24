package com.hynekbraun.composenotekeeper.data.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.ui.theme.*

object MockList {
    fun getMockList(): List<NoteModel> {
        return listOf(
            NoteModel(
                id = 1,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundCyan.toArgb()
            ),
            NoteModel(
                id = 2,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundBlue.toArgb()
            ),
            NoteModel(
                id = 3,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundCyan.toArgb()
            ),
            NoteModel(
                id = 4,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundGreen.toArgb()
            ),
            NoteModel(
                id = 5,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundRed.toArgb()
            ),
            NoteModel(
                id = 6,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundBlue.toArgb()
            ),
            NoteModel(
                id = 7,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundCyan.toArgb()
            ),
            NoteModel(
                id = 8,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundGreen.toArgb()
            ),
            NoteModel(
                id = 9,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundRed.toArgb()
            ),
            NoteModel(
                id = 10,
                header = "header",
                content = "Content of the Note",
                date = "22/05",
                color = BackgroundYellow.toArgb()
            ),
        )
    }
}
