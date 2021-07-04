package com.myniprojects.compass.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PrimaryColorDark,
    primaryVariant = PrimaryColorDark,
    secondary = PrimaryColorDark,
    onSurface = Color.White,
    onBackground = Color.White
)

private val LightColorPalette = lightColors(
    primary = PrimaryColorLight,
    primaryVariant = PrimaryColorLight,
    secondary = PrimaryColorLight,
    onSurface = Color.Black,
    onBackground = Color.Black,
)

@Composable
fun CompassTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit)
{
    val colors = if (darkTheme)
    {
        DarkColorPalette
    }
    else
    {
        LightColorPalette
    }
    
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun ThemedPreview(
    darkTheme: Boolean = false,
    children: @Composable () -> Unit
)
{
    CompassTheme(darkTheme = darkTheme) {
        Surface {
            children()
        }
    }
}