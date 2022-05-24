package com.hynekbraun.composenotekeeper.presentation.createnote

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hynekbraun.composenotekeeper.data.NoteRepositoryImp
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel
@Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _state = mutableStateOf(CreateNoteState())
    val state: State<CreateNoteState> get() = _state

    fun onEvent(event: CreateNoteEvent) {
        when (event) {
            is CreateNoteEvent.OnColorChange -> {
                _state.value = state.value.copy(
                    color = event.color
                )
            }
            is CreateNoteEvent.OnContentChange -> {
                _state.value = state.value.copy(
                    content = event.content
                )
            }
            is CreateNoteEvent.OnHeaderChange -> {
                _state.value = state.value.copy(
                    header = event.header
                )
            }
            is CreateNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    if (state.value.header.isNotBlank() && state.value.content.isNotBlank())
                        repository.insertNote(
                            NoteModel(
                                id = 0,
                                header = state.value.header,
                                content = state.value.content,
                                date = formatDate(),
                                color = state.value.color
                            )
                        )
                    Log.d("TAG", "CreateNoteVM: Saved note: ${state.value.header}")
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(): String {
        val sdf = SimpleDateFormat("yy/MM/dd hh:mm")
        return sdf.format(Date())
    }
}