package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ya.school.todoapp.R
import ya.school.todoapp.data.TodoItem

@Composable
fun ImportancePicker(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onPick: (TodoItem.Importance) -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = modifier
            .shadow(elevation = 2.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.importance_none)) },
            onClick = { onPick(TodoItem.Importance.Regular) }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.importance_low)) },
            onClick = { onPick(TodoItem.Importance.Low) }
        )
        DropdownMenuItem(
            text = {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.double_accent),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.importance_high),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                   },
            onClick = { onPick(TodoItem.Importance.Urgent) }
        )
    }
}

@Composable
fun ImportanceRow(
    importance: TodoItem.Importance,
    onPick: (TodoItem.Importance) -> Unit
) {
    Box {
        var importancePickerExpanded by remember {
            mutableStateOf(false)
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .clickable { importancePickerExpanded = true }
                .fillMaxWidth()
        ) {
            ToDoMainText(text = stringResource(id = R.string.importance))
            ToDoSubText(
                text = when (importance) {
                    TodoItem.Importance.Low -> stringResource(id = R.string.importance_low)
                    TodoItem.Importance.Regular -> stringResource(id = R.string.importance_none)
                    TodoItem.Importance.Urgent -> stringResource(id = R.string.importance_high)
                }
            )
        }
        ImportancePicker(
            expanded = importancePickerExpanded,
            onDismiss = { importancePickerExpanded = false },
            onPick = {
                onPick(it)
                importancePickerExpanded = false
            }
        )
    }
}