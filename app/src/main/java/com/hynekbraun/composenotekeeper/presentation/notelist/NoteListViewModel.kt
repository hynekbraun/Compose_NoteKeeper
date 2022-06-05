package com.hynekbraun.composenotekeeper.presentation.notelist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
@Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _state = mutableStateOf(NoteListState())
    val state: State<NoteListState> = _state

    private var getNotesJob: Job? = null

    private var lastDeletedNote: NoteModel? = null

    val query = mutableStateOf("")

    init {
        getNotes()
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.DeleteNote -> {
                lastDeletedNote = event.note
                deleteNote(event.note)
            }
            is NoteListEvent.OnSortToggle -> {
                _state.value = state.value.copy(showSelection = !state.value.showSelection)
            }
            is NoteListEvent.ChangeOrder -> {
                Log.d("ORDER", "ViewModel ChangeOrder event ${_state.value.noteOrder}")
                if (state.value.noteOrder == event.noteOrder) {
                    return
                }
                _state.value = state.value.copy(noteOrder = event.noteOrder)
                getNotes()
            }
            is NoteListEvent.OnRestoreNote -> {
                viewModelScope.launch {
                    repository.insertNote(lastDeletedNote ?: return@launch)
                    lastDeletedNote = null
                }
            }
            is NoteListEvent.OnQueryChanged -> {
                Log.d("SEARCH", "ViewModel onQueryChangedEvent start ${event.query}")
                query.value = event.query
                Log.d("SEARCH", "ViewModel onQueryChangedEvent start $query")
                searchNotes()
            }
            NoteListEvent.OnClearClicked -> {
                query.value = ""
                getNotes()
            }
        }
    }

    private fun deleteNote(note: NoteModel) {
        viewModelScope.launch {
            delay(500L)
            repository.deleteNote(note)
        }
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        Log.d("ORDER", "ViewModel: Current order: ${_state.value.noteOrder}")
        getNotesJob = repository.getNoteList(noteOrder = _state.value.noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = _state.value.noteOrder
                )
            }
            .launchIn(viewModelScope)
    }

    private fun searchNotes() {
        Log.d("SEARCH", "ViewModel: Search query: $query")
        repository.searchNotes(query = query.value)
            .onEach { notes ->
                Log.d("SEARCH", "ViewModel: List size fetched: ${notes.size}")
                _state.value = state.value.copy(
                    notes = notes
                )
            }
            .launchIn(viewModelScope)
    }
}

