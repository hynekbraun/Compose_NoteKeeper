package com.hynekbraun.composenotekeeper.presentation.notedetail

sealed class EditNoteEvent{
    data class OnHeaderChanged(val header: String): EditNoteEvent()
    data class OnContentChanged(val content: String): EditNoteEvent()
    data class OnColorChange(val color: Int ): EditNoteEvent()
    object UpdateNote: EditNoteEvent()
}