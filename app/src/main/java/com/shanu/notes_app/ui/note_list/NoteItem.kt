package com.shanu.notes_app.ui.note_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shanu.notes_app.data.models.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    note: Note,
    onEvent: (NoteListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = note.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                }
                note.content?.let { content ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if(content.length > 50) content.slice(1..50).plus("...") else content
                    )
                }

            }
            Row() {
                IconButton(onClick = {
                    onEvent(NoteListEvent.OnDeleteNoteClick(note))
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete",)
                }
                Checkbox(
                    checked = note.isDone,
                    onCheckedChange = { isChecked ->
                        onEvent(NoteListEvent.OnDoneChange(note, isChecked))
                    }
                )
            }

        }
    }
}