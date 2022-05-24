package com.hynekbraun.composenotekeeper.presentation.notelist

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hynekbraun.composenotekeeper.data.NoteRepositoryImp
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
@Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _state = mutableStateOf(NoteListState())
    val state: State<NoteListState> = _state

    init {
        loadNotes()
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.DeleteNote -> {
                deleteNote(event.note)
            }
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            repository.getNoteList().collect { notes ->
                _state.value = state.value.copy(notes = notes)
            }
        }
    }

    private fun deleteNote(note: NoteModel) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}