package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ya.school.todoapp.R
import ya.school.todoapp.domain.entity.ThemeMode
import ya.school.todoapp.presentation.ui.theme.AppTheme

@Composable
fun ThemePicker(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onPick: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = modifier
            .shadow(elevation = 2.dp)
            .background(AppTheme.colors.backElevated)
    ) {
        DropdownMenuItem(
            text = { Text(
                text = stringResource(id = R.string.theme_light)) },
            onClick = { onPick(ThemeMode.Light) }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.theme_dark)) },
            onClick = { onPick(ThemeMode.Dark) }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.theme_system)) },
            onClick = { onPick(ThemeMode.System) }
        )
    }
}

@Composable
fun ThemeRow(
    importance: ThemeMode,
    onPick: (ThemeMode) -> Unit
) {
    Box {
        var themePickerExpanded by remember {
            mutableStateOf(false)
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .clickable { themePickerExpanded = true }
                .fillMaxWidth()
        ) {
            ToDoMainText(text = stringResource(id = R.string.theme))
            ToDoSubText(
                text = when (importance) {
                    ThemeMode.Light -> stringResource(id = R.string.theme_light)
                    ThemeMode.Dark -> stringResource(id = R.string.theme_dark)
                    ThemeMode.System -> stringResource(id = R.string.theme_system)
                }
            )
        }
        ThemePicker(
            expanded = themePickerExpanded,
            onDismiss = { themePickerExpanded = false },
            onPick = {
                onPick(it)
                themePickerExpanded = false
            }
        )
    }
}

@Preview
@Composable
fun ThemePickerPreview() {
    ThemePicker(expanded = true, onDismiss = { }, onPick = { })
}