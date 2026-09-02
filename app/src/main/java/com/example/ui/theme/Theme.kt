package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val BrasileiraoDarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.Black,
    primaryContainer = GreenDark,
    onPrimaryContainer = Color.White,
    secondary = YellowGold,
    onSecondary = Color.Black,
    secondaryContainer = DarkSurfaceHighlight,
    onSecondaryContainer = YellowGold,
    tertiary = BlueLiberta,
    onTertiary = Color.Black,
    background = DarkBg,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = DarkBorder,
    error = RedZ4,
    onError = Color.White
)

private val BrasileiraoLightColorScheme = lightColorScheme(
    primary = GreenDark,
    onPrimary = Color.White,
    primaryContainer = GreenPrimary,
    onPrimaryContainer = Color.Black,
    secondary = YellowGold,
    onSecondary = Color.Black,
    secondaryContainer = LightSurfaceElevated,
    onSecondaryContainer = TextPrimaryLight,
    tertiary = BlueLiberta,
    onTertiary = Color.White,
    background = LightBg,
    onBackground = TextPrimaryLight,
    surface = LightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = LightSurfaceElevated,
    onSurfaceVariant = TextSecondaryLight,
    outline = LightBorder,
    error = RedZ4,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to sleek sports dark mode
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) BrasileiraoDarkColorScheme else BrasileiraoLightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
