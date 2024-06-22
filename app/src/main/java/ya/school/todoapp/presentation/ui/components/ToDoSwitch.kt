package ya.school.todoapp.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable

@Composable
fun ToDoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.primaryContainer,
            checkedTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
            uncheckedThumbColor = MaterialTheme.colorScheme.surfaceContainer,
            uncheckedTrackColor = MaterialTheme.colorScheme.onTertiaryContainer,
            uncheckedBorderColor = MaterialTheme.colorScheme.surface
        )
    )
}