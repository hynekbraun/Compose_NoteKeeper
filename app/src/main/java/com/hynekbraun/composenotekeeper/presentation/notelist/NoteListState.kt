package com.hynekbraun.composenotekeeper.presentation.notelist

import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.presentation.notelist.util.NoteOrder
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderType

data class NoteListState(
    val notes: List<NoteModel> = emptyList(),
    val showSelection: Boolean = false,
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),

    )