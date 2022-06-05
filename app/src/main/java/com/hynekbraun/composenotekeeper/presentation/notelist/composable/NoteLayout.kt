package com.hynekbraun.composenotekeeper.presentation.notelist.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hynekbraun.composenotekeeper.R
import com.hynekbraun.composenotekeeper.domain.model.NoteModel
import com.hynekbraun.composenotekeeper.presentation.composable.noRippleClickable


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
                .align(Alignment.BottomEnd)
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
