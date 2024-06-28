package ya.school.todoapp.presentation.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ya.school.todoapp.presentation.ui.theme.AppTheme

@Composable
fun FormTextInput(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String?
) {
    ElevatedContainer {
        TextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = false,
            modifier = modifier,
            placeholder = {
                hint?.let {
                    Text(text = it)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = AppTheme.colors.backSecondary,
                unfocusedContainerColor = AppTheme.colors.backSecondary,
                focusedTextColor = AppTheme.colors.labelPrimary,
                unfocusedTextColor = AppTheme.colors.labelPrimary,
                focusedPlaceholderColor = AppTheme.colors.labelTertiary,
                unfocusedPlaceholderColor = AppTheme.colors.labelTertiary
            )
        )
    }
}