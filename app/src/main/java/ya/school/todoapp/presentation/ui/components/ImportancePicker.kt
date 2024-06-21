package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
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
            text = { Text(text = "Нет") },
            onClick = { onPick(TodoItem.Importance.Regular) }
        )
        DropdownMenuItem(
            text = { Text(text = "Низкий") },
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
                        text = "Высокий",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                   },
            onClick = { onPick(TodoItem.Importance.Urgent) }
        )
    }
}