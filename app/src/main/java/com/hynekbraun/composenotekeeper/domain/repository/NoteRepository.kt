package com.hynekbraun.composenotekeeper.domain.repository

import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNoteList(): Flow<List<NoteModel>>

    suspend fun getNoteById(id: Int): NoteModel

    suspend fun deleteNote(note: NoteModel)

    suspend fun insertNote(note: NoteModel)

    suspend fun updateNote(note: NoteModel)
}