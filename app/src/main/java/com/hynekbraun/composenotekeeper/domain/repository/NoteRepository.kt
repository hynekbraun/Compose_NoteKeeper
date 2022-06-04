package com.hynekbraun.composenotekeeper.domain.repository

import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.presentation.notelist.util.NoteOrder
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderAscendance
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderType
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNoteList(
        noteOrder: NoteOrder
    ): Flow<List<NoteModel>>

    suspend fun getNoteById(id: Int): NoteModel

    suspend fun deleteNote(note: NoteModel)

    suspend fun insertNote(note: NoteModel)

    suspend fun updateNote(note: NoteModel)

     fun searchNotes(query: String): Flow<List<NoteModel>>
}