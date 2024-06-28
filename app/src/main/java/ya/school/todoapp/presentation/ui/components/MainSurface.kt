package ya.school.todoapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ya.school.todoapp.presentation.ui.theme.AppTheme

@Composable
fun MainSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(AppTheme.colors.backPrimary)
            .padding(12.dp)
            .fillMaxSize()
    ) {
        content()
    }
}