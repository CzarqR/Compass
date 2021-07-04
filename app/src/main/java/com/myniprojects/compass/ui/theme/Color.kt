package com.myniprojects.compass.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val PrimaryColorDark = Color(0xFF8bc34a)
val PrimaryColorLight = Color(0xFF83BB41)

val GradientBottomLight = Color(0xFFD2D2D2)
val GradientTopLight = Color(0xFFFFFFFF)

val GradientBottomDark = Color(0xFF000000)
val GradientTopDark = Color(0xFF28313b)

fun getGradientTop(isSystemDarkTheme: Boolean) =
        if (isSystemDarkTheme) GradientTopDark else GradientTopLight

fun getGradientBottom(isSystemDarkTheme: Boolean) =
        if (isSystemDarkTheme) GradientBottomDark else GradientBottomLight

fun getGradientBrush(isSystemDarkTheme: Boolean): Brush = Brush.verticalGradient(
    listOf(
        getGradientTop(isSystemDarkTheme), getGradientBottom(isSystemDarkTheme)
    ),
)