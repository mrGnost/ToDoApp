package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlineDatePicker(
    onConfirm: (Date?) -> Unit,
    onDismiss: () -> Unit
) {
    val dateState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            ToDoTextButton(
                text = "Готово",
                onClick = {
                    onConfirm(dateState.selectedDateMillis?.let { Date(it) })
                }
            )
        },
        dismissButton = {
            ToDoTextButton(
                text = "Отмена",
                onClick = onDismiss
            )
        }
    ) {
        DatePicker(
            state = dateState,
            showModeToggle = false
        )
    }
}

@Composable
fun DeadlineRow(
    date: String?,
    onDateChanged: (Date?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var checked by remember {
            mutableStateOf(false)
        }
        var showCalendar by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .clickable {
                    if (checked)
                        showCalendar = true
                }
                .weight(1f)
        ) {
            ToDoMainText(text = "Сделать до")
            if (date != null && checked) {
                Text(
                    text = date,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
        ToDoSwitch(
            checked = checked,
            onCheckedChange = { checked = !checked }
        )
        if (showCalendar) {
            DeadlineDatePicker(
                onConfirm = {
                    onDateChanged(it)
                    showCalendar = false
                },
                onDismiss = { showCalendar = false }
            )
        }
    }
}