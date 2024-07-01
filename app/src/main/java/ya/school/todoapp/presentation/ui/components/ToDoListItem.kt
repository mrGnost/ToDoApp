package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ya.school.todoapp.R
import ya.school.todoapp.data.TodoItem
import ya.school.todoapp.presentation.ui.theme.AppTheme
import ya.school.todoapp.presentation.ui.util.DateUtil.toDateString
import java.util.Date

@Composable
fun ToDoListItem(
    item: TodoItem,
    onCheckedChange: (String, Boolean) -> Unit,
    onInfoClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .background(AppTheme.colors.backSecondary)
            .clickable { onInfoClick(item.id) }
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToDoCheckBox(
            id = item.id,
            checked = item.isDone,
            important = item.importance == TodoItem.Importance.Urgent,
            onCheckedChange = onCheckedChange
        )
        ToDoItemContent(
            modifier = Modifier
                .weight(1f),
            text = item.text,
            date = item.deadline?.toDateString(),
            isDone = item.isDone,
            important = item.importance
        )
        Icon(
            Icons.Outlined.Info,
            null,
            tint = AppTheme.colors.labelTertiary
        )
    }
}

@Composable
fun ToDoItemContent(
    modifier: Modifier = Modifier,
    text: String,
    date: String?,
    isDone: Boolean,
    important: TodoItem.Importance
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImportanceSign(important = important)
        Column {
            TaskPreviewText(
                text = text,
                isCrossed = isDone
            )
            TaskDateText(date = date)
        }
    }
}

@Composable
fun ImportanceSign(
    important: TodoItem.Importance
) {
    val icon = when (important) {
        TodoItem.Importance.Low -> R.drawable.vertical_arrow
        TodoItem.Importance.Urgent -> R.drawable.double_accent
        else -> null
    }
    icon?.let {
        Image(
            painter = painterResource(id = it),
            contentDescription = null
        )
    }
}

@Composable
fun TaskDateText(
    date: String?
) {
    date?.let {
        ToDoSubText(text = it)
    }
}

@Composable
fun TaskPreviewText(
    text: String,
    isCrossed: Boolean
) {
    val color = with(AppTheme.colors) {
        if (isCrossed)
            labelTertiary
        else
            labelPrimary
    }
    val decorator = if (isCrossed) TextDecoration.LineThrough else null


    Text(
        text = text,
        color = color,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle(
            textDecoration = decorator
        )
    )
}

@Composable
fun ToDoCheckBox(
    id: String,
    checked: Boolean,
    important: Boolean,
    onCheckedChange: (String, Boolean) -> Unit
) {
    Checkbox(
        checked = checked,
        colors = CheckboxDefaults.colors(
            checkedColor = AppTheme.colors.green,
            uncheckedColor = with (AppTheme.colors) {
                if (important)
                    red
                else
                    supportSeparator
            },

        ),
        onCheckedChange = {
            onCheckedChange(id, !checked)
        }
    )
}

@Preview
@Composable
fun ToDoListItemPreviewImportant() {
    val item = TodoItem(
        "0",
        "элемент",
        TodoItem.Importance.Urgent,
        isDone = false,
        createdAt = Date(0)
    )
    ToDoListItem(item = item, onCheckedChange = {x, y -> }) {

    }
}

@Preview
@Composable
fun ToDoListItemPreviewLow() {
    val item = TodoItem(
        "0",
        "элемент",
        TodoItem.Importance.Low,
        isDone = false,
        createdAt = Date(0)
    )
    ToDoListItem(item = item, onCheckedChange = {x, y -> }) {

    }
}

@Preview
@Composable
fun ToDoListItemPreviewChecked() {
    val item = TodoItem(
        "0",
        "элемент",
        TodoItem.Importance.Regular,
        isDone = true,
        createdAt = Date(0)
    )
    ToDoListItem(item = item, onCheckedChange = {x, y -> }) {

    }
}