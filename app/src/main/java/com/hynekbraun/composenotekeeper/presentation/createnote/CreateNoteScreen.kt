package com.hynekbraun.composenotekeeper.presentation.createnote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hynekbraun.composenotekeeper.R
import com.hynekbraun.composenotekeeper.presentation.composable.ColorPallet
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateNoteScreen(
    viewModel: CreateNoteViewModel = viewModel(),
    onSaveClicked: () -> Unit
) {
    val note by remember {
        viewModel.state
    }
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = 1) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CreateNoteViewModel.CreateNoteUiEvent.SaveNote -> {
                    onSaveClicked()
                }
                is CreateNoteViewModel.CreateNoteUiEvent.ShowErrorSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is CreateNoteViewModel.CreateNoteUiEvent.ShowWrongInputSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(CreateNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = stringResource(
                        R.string.noteDetail_iconDesc_save
                    )
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(note.color))
                .padding(6.dp)
        ) {
            ColorPallet(onColorChange = {
                viewModel.onEvent(CreateNoteEvent.OnColorChange(color = it))
            }, noteColor = note.color)

            BasicTextField(
                value = note.header,
                onValueChange = {
                    viewModel.onEvent(CreateNoteEvent.OnHeaderChange(header = it))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.h3,
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {

                        if (note.header.isBlank()) {
                            Text(
                                stringResource(id = R.string.general_header),
                                style = MaterialTheme.typography.h3
                            )
                        }
                        // <-- Add this
                        innerTextField()
                    }
                })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(2.dp)
                    .background(color = Color.Black)
            )

            BasicTextField(
                value = note.content,
                onValueChange = {
                    viewModel.onEvent(CreateNoteEvent.OnContentChange(content = it))
                },
                modifier = Modifier.fillMaxSize(),
                textStyle = MaterialTheme.typography.body1,
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {

                        if (note.content.isBlank()) {
                            Text(
                                stringResource(id = R.string.general_message),
                                style = MaterialTheme.typography.body1
                            )
                        }
                        // <-- Add this
                        innerTextField()
                    }
                }
            )
        }

    }
}
