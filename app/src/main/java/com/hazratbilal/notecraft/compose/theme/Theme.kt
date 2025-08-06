package com.hazratbilal.notecraft.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = DefaultBackground,
    secondary = DefaultBackground,
    tertiary = DefaultColor,
    background = DefaultBackground,
    surface = DefaultBackground
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

}