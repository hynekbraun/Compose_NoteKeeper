package com.hynekbraun.composenotekeeper.presentation.notelist

import com.hynekbraun.composenotekeeper.domain.model.NoteModel

data class NoteListState(
    val notes: List<NoteModel> = emptyList()
)