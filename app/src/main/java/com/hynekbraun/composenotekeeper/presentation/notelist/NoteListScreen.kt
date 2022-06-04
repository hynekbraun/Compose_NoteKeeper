package com.hynekbraun.composenotekeeper.presentation

import android.annotation.SuppressLint
import android.widget.SearchView
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.hynekbraun.composenotekeeper.presentation.notelist.composable.NoteLayout
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
            .fillMaxSize()
            .padding(8.dp),
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
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            CustomSearchBar(
                modifier = Modifier.fillMaxWidth(),
                onQueryChanged = { viewModel.onEvent(NoteListEvent.onQueryChanged(it)) },
                query = viewModel.query.value,
                showClearButton = false,
                onClearClick = { },
                onSortToggleClicked = { viewModel.onEvent(NoteListEvent.OnSortToggle) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    query: String,
    showClearButton: Boolean,
    onClearClick: () -> Unit,
    onSortToggleClicked: () -> Unit

) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            maxLines = 1,
            value = query,
            onValueChange = { onQueryChanged(it) },
            placeholder = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(
                        R.string.appbar_search_icon_description
                    )
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.ic_clear
                            ),
                            contentDescription = stringResource(R.string.appbar_clear_search_description)
                        )
                    }
                }
            })
        IconButton(onClick = { onSortToggleClicked() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = stringResource(
                    R.string.noteList_iconDesc_sort
                )
            )
        }
    }
}


@Preview
@Composable
fun PreviewSearchBar() {
    Surface {
        CustomSearchBar(
            modifier = Modifier.fillMaxWidth(),
            onQueryChanged = {},
            query = "",
            showClearButton = false,
            onSortToggleClicked = {},
            onClearClick = {}
        )
    }
}