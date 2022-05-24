package com.hynekbraun.composenotekeeper.presentation.notedetail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.hynekbraun.composenotekeeper.ui.theme.BackgroundYellow

data class EditNoteState(
    val header: String = "",
    val content: String = "",
    val color: Int = BackgroundYellow.toArgb()
)
