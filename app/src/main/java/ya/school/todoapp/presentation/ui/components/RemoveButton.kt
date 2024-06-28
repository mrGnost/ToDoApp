package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ya.school.todoapp.presentation.ui.theme.AppTheme

@Composable
fun RemoveButton(
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                if (isActive)
                    onClick()
            }
    ) {
        val color = with (AppTheme.colors) {
            if (isActive)
                red
            else
                labelDisable
        }

        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Удалить",
            tint = color
        )
        Text(
            text = "Удалить",
            color = color
        )
    }
}