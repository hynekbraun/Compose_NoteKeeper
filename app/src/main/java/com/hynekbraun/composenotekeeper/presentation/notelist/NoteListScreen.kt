package com.hynekbraun.composenotekeeper.presentation

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
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hynekbraun.composenotekeeper.R
import com.hynekbraun.composenotekeeper.presentation.notelist.NoteListEvent
import com.hynekbraun.composenotekeeper.presentation.notelist.NoteListViewModel
import com.hynekbraun.composenotekeeper.presentation.notelist.util.NoteOrder
import com.hynekbraun.composenotekeeper.presentation.notelist.util.OrderType

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = viewModel(),
    onNoteClick: (id: Int) -> Unit,
    onCreateClick: () -> Unit

) {
    val state by viewModel.state
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
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
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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
                    onOrderChange = { viewModel.onEvent(NoteListEvent.ChangeOrder(it)) },
                    noteOrder = state.noteOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                )
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteLayout(
                        modifier = Modifier
                            .fillMaxSize(),
                        note,
                        onNoteClick = onNoteClick
                    )
                }
            }
        }
    }
}

@Composable
fun NoteLayout(
    modifier: Modifier,
    note: NoteModel,
    onNoteClick: (id: Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(6.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(color = Color(note.color))
            .padding(6.dp)
            .clickable { onNoteClick(note.id) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                .padding(4.dp)
                .background(color = Color.DarkGray)
                .fillMaxWidth()
                .height(1.dp)
                .clip(MaterialTheme.shapes.large)
        )
        Text(
            text = note.content,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Header,
                onSelect = { onOrderChange(NoteOrder.Header(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}

@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.onBackground
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}