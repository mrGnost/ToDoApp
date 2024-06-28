package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ya.school.todoapp.R
import ya.school.todoapp.presentation.ui.theme.AppTheme
import ya.school.todoapp.presentation.ui.util.DateUtil.getYear
import ya.school.todoapp.presentation.ui.util.DateUtil.toWeekDayDateString
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
                text = stringResource(id = R.string.done),
                onClick = {
                    onConfirm(dateState.selectedDateMillis?.let { Date(it) })
                }
            )
        },
        dismissButton = {
            ToDoTextButton(
                text = stringResource(id = R.string.cancel),
                onClick = onDismiss
            )
        }
    ) {
        DatePicker(
            state = dateState,
            showModeToggle = false,
            title = {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = getYear(dateState.selectedDateMillis ?: 0).toString()
                )
            },
            headline = {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = Date(dateState.selectedDateMillis ?: 0).toWeekDayDateString()
                )
            },
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = AppTheme.colors.blue,
                selectedYearContainerColor = AppTheme.colors.blue,
                dividerColor = AppTheme.colors.blue,
                todayDateBorderColor = AppTheme.colors.blue,
            )
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
                .height(80.dp)
                .weight(1f)
        ) {
            ToDoMainText(text = stringResource(id = R.string.do_before))
            if (date != null && checked) {
                Text(
                    text = date,
                    color = AppTheme.colors.blue
                )
            }
        }
        ToDoSwitch(
            checked = checked,
            onCheckedChange = {
                checked = !checked
                showCalendar = true
            }
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