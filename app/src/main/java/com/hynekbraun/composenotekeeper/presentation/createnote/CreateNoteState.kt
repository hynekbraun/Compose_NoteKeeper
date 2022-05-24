package com.hynekbraun.composenotekeeper.presentation.createnote

import android.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.hynekbraun.composenotekeeper.ui.theme.BackgroundYellow

data class CreateNoteState(
    val header: String = "",
    val content: String = "",
    val color: Int = BackgroundYellow.toArgb()
)
