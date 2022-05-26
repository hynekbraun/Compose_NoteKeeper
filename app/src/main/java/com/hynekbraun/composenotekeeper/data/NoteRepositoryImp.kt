package com.hynekbraun.composenotekeeper.data

import com.hynekbraun.composenotekeeper.data.local.NoteDAO
import com.hynekbraun.composenotekeeper.data.model.toModel
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.domain.model.toEntity
import com.hynekbraun.composenotekeeper.domain.repository.NoteRepository
import com.hynekbraun.composenotekeeper.presentation.notelist.util.NoteOrder
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImp(private val dao: NoteDAO) : NoteRepository {
    override fun getNoteList(
        noteOrder: NoteOrder
    ): Flow<List<NoteModel>> {
        val notesList: Flow<List<NoteModel>> = dao.getNotes().map { it.map { it.toModel() } }
        return notesList.map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Header -> notes.sortedBy { it.header.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.date }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Header -> notes.sortedByDescending { it.header.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.date }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
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