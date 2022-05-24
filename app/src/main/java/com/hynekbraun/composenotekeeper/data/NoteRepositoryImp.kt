package com.hynekbraun.composenotekeeper.data

import com.hynekbraun.composenotekeeper.data.local.NoteDAO
import com.hynekbraun.composenotekeeper.data.local.NoteDatabase
import com.hynekbraun.composenotekeeper.data.model.toModel
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.domain.model.toEntity
import com.hynekbraun.composenotekeeper.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

class NoteRepositoryImp(private val dao: NoteDAO) : NoteRepository {
    override fun getNoteList(): Flow<List<NoteModel>> {
        return dao.getNotes()
            .map { it.map { it.toModel() } }
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