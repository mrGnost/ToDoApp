package ya.school.todoapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

fun lightColors(): AppColors = AppColors(
    supportSeparator = LightPalette.SupportSeparator,
    supportOverlay = LightPalette.SupportOverlay,
    labelPrimary = LightPalette.LabelPrimary,
    labelSecondary = LightPalette.LabelSecondary,
    labelTertiary = LightPalette.LabelTertiary,
    labelDisable = LightPalette.LabelDisable,
    red = LightPalette.Red,
    pink = LightPalette.Pink,
    green = LightPalette.Green,
    blue = LightPalette.Blue,
    lightBlue = LightPalette.LightBlue,
    gray = LightPalette.Gray,
    grayLight = LightPalette.GrayLight,
    white = LightPalette.White,
    backPrimary = LightPalette.BackPrimary,
    backSecondary = LightPalette.BackSecondary,
    backElevated = LightPalette.BackElevated,
    material = materialLightColors,
    isLight = true
)

fun darkColors(): AppColors = AppColors(
    supportSeparator = DarkPalette.SupportSeparator,
    supportOverlay = DarkPalette.SupportOverlay,
    labelPrimary = DarkPalette.LabelPrimary,
    labelSecondary = DarkPalette.LabelSecondary,
    labelTertiary = DarkPalette.LabelTertiary,
    labelDisable = DarkPalette.LabelDisable,
    red = DarkPalette.Red,
    pink = DarkPalette.Pink,
    green = DarkPalette.Green,
    blue = DarkPalette.Blue,
    lightBlue = DarkPalette.LightBlue,
    gray = DarkPalette.Gray,
    grayLight = DarkPalette.GrayLight,
    white = DarkPalette.White,
    backPrimary = DarkPalette.BackPrimary,
    backSecondary = DarkPalette.BackSecondary,
    backElevated = DarkPalette.BackElevated,
    material = materialDarkColors,
    isLight = false
)

val materialLightColors = lightColorScheme(
    primary = LightPalette.Green,
    primaryContainer = LightPalette.White,
    surface = LightPalette.White,
    surfaceVariant = LightPalette.GrayLight
)

val materialDarkColors = darkColorScheme(
    primary = DarkPalette.Green,
    primaryContainer = DarkPalette.White,
    surface = DarkPalette.White,
    surfaceVariant = DarkPalette.GrayLight
)

internal val LocalColors = staticCompositionLocalOf { lightColors() }
internal val LocalTypography = staticCompositionLocalOf { Typography }

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Composable
fun ToDoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when {
        darkTheme -> darkColors()
        else -> lightColors()
    }
    val rememberedColors = remember { colors.copy() }
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = LocalColors.current.material,
            content = content
        )
    }
}