package ya.school.todoapp.presentation.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ya.school.todoapp.presentation.ui.theme.AppTheme

@Composable
fun ToDoMainText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = AppTheme.colors.labelPrimary,
        style = AppTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun ToDoSubText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = AppTheme.colors.labelTertiary,
        style = AppTheme.typography.titleSmall,
        modifier = modifier
    )
}

@Preview
@Composable
fun ToDoMainTextPreview() {
    ToDoMainText(text = "Текст")
}

@Preview
@Composable
fun ToDoSubTextPreview() {
    ToDoSubText(text = "Текст")
}