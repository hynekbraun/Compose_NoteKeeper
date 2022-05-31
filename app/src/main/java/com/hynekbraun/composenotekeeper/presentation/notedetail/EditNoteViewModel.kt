package com.hynekbraun.composenotekeeper.presentation.notedetail

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.domain.repository.NoteRepository
import com.hynekbraun.composenotekeeper.presentation.navigation.NoteNavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel
@Inject constructor(
    val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _note = mutableStateOf(EditNoteState())
    val note: State<EditNoteState> get() = _note

    private val _eventFlow = MutableSharedFlow<EditNoteUiEvent>()
    val eventFlow get() = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>(NoteNavArgs.NOTE_ID)?.let { noteId ->
            viewModelScope.launch {
                repository.getNoteById(noteId).also { input ->
                    _note.value = note.value.copy(
                        header = input.header,
                        content = input.content,
                        color = input.color
                    )
                    currentNoteId = input.id
                }
            }
        }
    }

    fun onEvent(event: EditNoteEvent) {
        when (event) {
            is EditNoteEvent.OnColorChange -> {
                _note.value = note.value.copy(color = event.color)
            }
            is EditNoteEvent.OnContentChanged -> {
                _note.value = note.value.copy(content = event.content)
            }
            is EditNoteEvent.OnHeaderChanged -> {
                _note.value = note.value.copy(header = event.header)
            }
            EditNoteEvent.UpdateNote -> {
                viewModelScope.launch {
                    if (note.value.header.isNotBlank()) {
                        try {
                            repository.updateNote(
                                NoteModel(
                                    header = _note.value.header,
                                    content = _note.value.content,
                                    color = _note.value.color,
                                    id = currentNoteId!!,
                                    date = formatDate()
                                )
                            )
                            _eventFlow.emit(EditNoteUiEvent.EditNote)
                        } catch (e: Throwable) {
                            _eventFlow.emit(
                                EditNoteUiEvent.ShowErrorSnackbar(
                                    e.localizedMessage ?: "Could not save note"
                                )
                            )
                        }
                    } else {
                        _eventFlow.emit(
                            EditNoteUiEvent.ShowInoutSnackbar(
                                message = "Please fill in the header"

                            )
                        )
                    }
                }
            }
        }
    }

    sealed class EditNoteUiEvent {
        data class ShowErrorSnackbar(val message: String) : EditNoteUiEvent()
        data class ShowInoutSnackbar(val message: String) : EditNoteUiEvent()
        object EditNote : EditNoteUiEvent()
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(): String {
        val sdf = SimpleDateFormat("yy/MM/dd hh:mm a")
        return sdf.format(System.currentTimeMillis())
    }
}