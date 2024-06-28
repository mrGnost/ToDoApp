package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import ya.school.todoapp.presentation.ui.theme.AppTheme

@Composable
fun ToDoFAB(
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = CircleShape)
            .clip(CircleShape),
        containerColor = AppTheme.colors.blue,
        contentColor = AppTheme.colors.white
    ) {
        icon()
    }
}