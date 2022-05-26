package com.hynekbraun.composenotekeeper.presentation.notelist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.domain.repository.NoteRepository
import com.hynekbraun.composenotekeeper.presentation.notelist.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
@Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _state = mutableStateOf(NoteListState())
    val state: State<NoteListState> = _state

    private var getNotesJob: Job? = null

    init {
        loadNotes()
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.DeleteNote -> {
                deleteNote(event.note)
            }
            is NoteListEvent.OnSortToggle -> {
                _state.value = state.value.copy(showSelection = !state.value.showSelection)
            }
            is NoteListEvent.ChangeOrder -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
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

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = repository.getNoteList(noteOrder = _state.value.noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}