package com.hynekbraun.composenotekeeper.presentation.notelist

import com.hynekbraun.composenotekeeper.domain.model.NoteModel

sealed class NoteListEvent{
    data class DeleteNote(val note: NoteModel): NoteListEvent()
}