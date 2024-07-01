package ya.school.todoapp.presentation.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ya.school.todoapp.presentation.ui.theme.AppTheme

@Composable
fun ToDoTextButton(
    text: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Text(
            text = text.uppercase(),
            color = AppTheme.colors.blue
        )
    }
}

@Preview
@Composable
fun ToDoButtonPreview() {
    ToDoTextButton(text = "Кнопка") {
        
    }
}