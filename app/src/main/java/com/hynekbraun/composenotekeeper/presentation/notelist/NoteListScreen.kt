package com.hynekbraun.composenotekeeper.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hynekbraun.composenotekeeper.data.local.MockList
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.hynekbraun.composenotekeeper.R
import com.hynekbraun.composenotekeeper.presentation.notelist.NoteListViewModel
import com.hynekbraun.composenotekeeper.ui.theme.ComposeNoteKeeperTheme

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

    }
    LazyColumn() {
        items(state.notes) { note ->
            NoteLayout(
                modifier = Modifier
                    .fillMaxWidth(),
                note,
                onNoteClick = onNoteClick
            )
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