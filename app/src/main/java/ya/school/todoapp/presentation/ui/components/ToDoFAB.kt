package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
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
            .clip(CircleShape)
            .semantics {
                testTag = "add_item_btn"
            },
        containerColor = AppTheme.colors.blue,
        contentColor = AppTheme.colors.white
    ) {
        icon()
    }
}

@Preview
@Composable
fun ToDoFABPreview() {
    ToDoFAB(onClick = { }) {
        Icon(Icons.Default.Add, contentDescription = null)
    }
}