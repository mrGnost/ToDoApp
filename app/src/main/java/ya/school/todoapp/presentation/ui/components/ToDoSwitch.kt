package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ya.school.todoapp.presentation.ui.theme.AppTheme

@Composable
fun ToDoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = AppTheme.colors.blue,
            checkedTrackColor = AppTheme.colors.lightBlue,
            uncheckedThumbColor = AppTheme.colors.backElevated,
            uncheckedTrackColor = AppTheme.colors.supportOverlay,
            uncheckedBorderColor = AppTheme.colors.backPrimary
        )
    )
}

@Preview
@Composable
fun ToDoSwitchPreview() {
    Column {
        ToDoSwitch(false) {

        }
        ToDoSwitch(true) {

        }
    }
}