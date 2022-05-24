package com.hynekbraun.composenotekeeper.presentation.createnote

sealed class CreateNoteEvent {
    object SaveNote : CreateNoteEvent()
    data class OnHeaderChange(val header: String) : CreateNoteEvent()
    data class OnContentChange(val content: String) : CreateNoteEvent()
    data class OnColorChange(val color: Int) : CreateNoteEvent()
}
