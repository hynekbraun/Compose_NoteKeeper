package com.hynekbraun.composenotekeeper.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hynekbraun.composenotekeeper.R
import com.hynekbraun.composenotekeeper.presentation.composable.noRippleClickable
import com.hynekbraun.composenotekeeper.presentation.notelist.NoteListEvent
import com.hynekbraun.composenotekeeper.presentation.notelist.NoteListViewModel
import com.hynekbraun.composenotekeeper.presentation.notelist.composable.OrderSection
import com.hynekbraun.composenotekeeper.presentation.notelist.util.NoteOrder
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderAscendance
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderType
import com.hynekbraun.composenotekeeper.ui.theme.BackgroundRed
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = viewModel(),
    onNoteClick: (id: Int) -> Unit,
    onCreateClick: () -> Unit

) {
    val state by viewModel.state
    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onCreateClick()
                },
                backgroundColor = MaterialTheme.colors.primary
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add note"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(text = stringResource(id = R.string.app_name))
                IconButton(onClick = { viewModel.onEvent(NoteListEvent.OnSortToggle) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sort),
                        contentDescription = stringResource(
                            R.string.noteList_iconDesc_sort
                        )
                    )
                }
            }
            AnimatedVisibility(
                visible = viewModel.state.value.showSelection,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    noteOrder = state.noteOrder,
                    onOrderChange = { viewModel.onEvent(NoteListEvent.ChangeOrder(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(state.notes) { note ->
                    NoteLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillParentMaxHeight(0.2f),
                        note,
                        onNoteClick = onNoteClick,
                        onDeleteClicked = {
                            viewModel.onEvent(NoteListEvent.DeleteNote(it))
                            scope.launch {
                                val event = scaffoldState
                                    .snackbarHostState
                                    .showSnackbar(
                                        message = "Are you sure?",
                                        actionLabel = "Undo"
                                    )
                                if (event == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NoteListEvent.OnRestoreNote)
                                }
                            }

                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteLayout(
    modifier: Modifier = Modifier,
    note: NoteModel,
    onNoteClick: (id: Int) -> Unit,
    onDeleteClicked: (note: NoteModel) -> Unit
) {

    Box(modifier = modifier) {
        Column(
            modifier = modifier
                .clip(MaterialTheme.shapes.small)
                .background(color = Color(note.color))
                .padding(8.dp)
                .noRippleClickable { onNoteClick(note.id) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = note.header,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = note.date)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(1.dp)
                    .background(color = Color.DarkGray)
                    .clip(MaterialTheme.shapes.large)
            )
            Text(
                text = note.content,
                modifier = Modifier
                    .fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
            )
        }
        IconButton(
            modifier = Modifier
                .align(BottomEnd)
                .clip(CircleShape)
                .background(Color(note.color)),
            onClick = { onDeleteClicked(note) }) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_delete
                ),
                contentDescription = stringResource(
                    R.string.noteList_imageDesc_deleteNote
                )
            )
        }

    }
}
