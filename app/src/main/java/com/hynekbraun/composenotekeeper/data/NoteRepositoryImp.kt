package com.hynekbraun.composenotekeeper.data

import android.util.Log
import com.hynekbraun.composenotekeeper.data.local.NoteDAO
import com.hynekbraun.composenotekeeper.data.model.toModel
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.domain.model.toEntity
import com.hynekbraun.composenotekeeper.domain.repository.NoteRepository
import com.hynekbraun.composenotekeeper.presentation.notelist.util.NoteOrder
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderAscendance
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImp(private val dao: NoteDAO) : NoteRepository {
    override fun getNoteList(
        noteOrder: NoteOrder
    ): Flow<List<NoteModel>> {
        val notesList: Flow<List<NoteModel>> =
            dao.getNotes().map { list ->
                list.map { note ->
                    note.toModel()
                }
            }
        return notesList.map { notes ->
            Log.d("ORDER", "Repository - $noteOrder")
            when (noteOrder) {
                NoteOrder(OrderType.HEADER, OrderAscendance.ASCENDING) ->
                    notes.sortedBy { it.header.lowercase().trim() }
                NoteOrder(OrderType.DATE, OrderAscendance.ASCENDING) ->
                    notes.sortedBy { it.date }
                NoteOrder(OrderType.COLOR, OrderAscendance.ASCENDING) ->
                    notes.sortedBy { it.color }
                NoteOrder(OrderType.HEADER, OrderAscendance.DESCENDING) ->
                    notes.sortedByDescending { it.header }
                NoteOrder(OrderType.DATE, OrderAscendance.DESCENDING) ->
                    notes.sortedByDescending { it.date }
                NoteOrder(OrderType.COLOR, OrderAscendance.DESCENDING) ->
                    notes.sortedByDescending { it.color }
                else -> {
                    Log.d("ORDER", "repository: Random order")
                    notes
                }
            }
        }
    }

    override suspend fun getNoteById(id: Int): NoteModel {
        return dao.getNoteById(id).toModel()
    }

    override suspend fun deleteNote(note: NoteModel) {
        dao.deleteNote(note.toEntity())
    }

    override suspend fun insertNote(note: NoteModel) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: NoteModel) {
        dao.updateNote(note.toEntity())
    }
}