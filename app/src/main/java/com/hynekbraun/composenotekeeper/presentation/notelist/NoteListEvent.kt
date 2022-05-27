package com.hynekbraun.composenotekeeper.presentation.notelist

import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.presentation.notelist.util.NoteOrder

sealed class NoteListEvent {
    data class DeleteNote(val note: NoteModel) : NoteListEvent()
    object OnSortToggle : NoteListEvent()
    data class ChangeOrder(val noteOrder: NoteOrder) : NoteListEvent()
    object OnRestoreNote: NoteListEvent()

}