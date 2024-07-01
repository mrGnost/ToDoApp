package ya.school.todoapp.presentation.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun LightSchemePreview() {
    Column {
        with(LightPalette) {
            ColorStripe(color = SupportSeparator, name = "SupportSeparator", isLight = false)
            ColorStripe(color = SupportOverlay, name = "SupportOverlay", isLight = false)
            ColorStripe(color = LabelPrimary, name = "LabelPrimary", isLight = false)
            ColorStripe(color = LabelSecondary, name = "LabelSecondary", isLight = false)
            ColorStripe(color = LabelTertiary, name = "LabelTertiary", isLight = false)
            ColorStripe(color = LabelDisable, name = "LabelDisable", isLight = false)
            ColorStripe(color = Red, name = "Red", isLight = false)
            ColorStripe(color = Green, name = "Green", isLight = false)
            ColorStripe(color = Blue, name = "Blue", isLight = false)
            ColorStripe(color = LightBlue, name = "LightBlue", isLight = false)
            ColorStripe(color = Gray, name = "Gray", isLight = false)
            ColorStripe(color = GrayLight, name = "GrayLight", isLight = true)
            ColorStripe(color = White, name = "White", isLight = true)
            ColorStripe(color = Pink, name = "Pink", isLight = true)
            ColorStripe(color = BackPrimary, name = "BackPrimary", isLight = true)
            ColorStripe(color = BackSecondary, name = "BackSecondary", isLight = true)
            ColorStripe(color = BackElevated, name = "BackElevated", isLight = true)
        }
    }
}

@Preview
@Composable
fun DarkSchemePreview() {
    Column {
        with(DarkPalette) {
            ColorStripe(color = SupportSeparator, name = "SupportSeparator", isLight = false)
            ColorStripe(color = SupportOverlay, name = "SupportOverlay", isLight = false)
            ColorStripe(color = LabelPrimary, name = "LabelPrimary", isLight = true)
            ColorStripe(color = LabelSecondary, name = "LabelSecondary", isLight = false)
            ColorStripe(color = LabelTertiary, name = "LabelTertiary", isLight = false)
            ColorStripe(color = LabelDisable, name = "LabelDisable", isLight = false)
            ColorStripe(color = Red, name = "Red", isLight = false)
            ColorStripe(color = Green, name = "Green", isLight = false)
            ColorStripe(color = Blue, name = "Blue", isLight = false)
            ColorStripe(color = LightBlue, name = "LightBlue", isLight = false)
            ColorStripe(color = Gray, name = "Gray", isLight = false)
            ColorStripe(color = GrayLight, name = "GrayLight", isLight = false)
            ColorStripe(color = White, name = "White", isLight = true)
            ColorStripe(color = Pink, name = "Pink", isLight = true)
            ColorStripe(color = BackPrimary, name = "BackPrimary", isLight = false)
            ColorStripe(color = BackSecondary, name = "BackSecondary", isLight = false)
            ColorStripe(color = BackElevated, name = "BackElevated", isLight = false)
        }
    }
}

@Preview
@Composable
fun TypographyPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Large title", style = AppTheme.typography.titleLarge)
        Text(text = "Title", style = AppTheme.typography.titleMedium)
        Text(text = "BUTTON", style = AppTheme.typography.labelLarge)
        Text(text = "Body", style = AppTheme.typography.bodyLarge)
        Text(text = "Subhead", style = AppTheme.typography.titleSmall)
    }
}

@Composable
fun ColorStripe(color: Color, name: String, isLight: Boolean) {
    Box(
        modifier = Modifier
            .background(color)
            .height(40.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = if (isLight) Color.Black else Color.White
        )
    }
}