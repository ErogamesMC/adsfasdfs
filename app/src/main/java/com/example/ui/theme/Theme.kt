package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = StardewGreenPrimaryDark,
    onPrimary = StardewGreenOnPrimaryDark,
    primaryContainer = StardewGreenContainerDark,
    onPrimaryContainer = StardewOnGreenContainerDark,
    secondary = HarvestGoldSecondaryDark,
    onSecondary = HarvestGoldOnSecondaryDark,
    secondaryContainer = HarvestGoldContainerDark,
    onSecondaryContainer = HarvestGoldOnContainerDark,
    background = ParchmentBackgroundDark,
    onBackground = ParchmentOnSurfaceDark,
    surface = ParchmentSurfaceDark,
    onSurface = ParchmentOnSurfaceDark,
    surfaceVariant = ParchmentSurfaceVariantDark,
    onSurfaceVariant = ParchmentOnSurfaceVariantDark,
    outline = StardewOutline
)

private val LightColorScheme = lightColorScheme(
    primary = StardewGreenPrimary,
    onPrimary = StardewGreenOnPrimary,
    primaryContainer = StardewGreenContainer,
    onPrimaryContainer = StardewOnGreenContainer,
    secondary = HarvestGoldSecondary,
    onSecondary = HarvestGoldOnSecondary,
    secondaryContainer = HarvestGoldContainer,
    onSecondaryContainer = HarvestGoldOnContainer,
    background = ParchmentBackground,
    onBackground = ParchmentOnSurface,
    surface = ParchmentSurface,
    onSurface = ParchmentOnSurface,
    surfaceVariant = ParchmentSurfaceVariant,
    onSurfaceVariant = ParchmentOnSurfaceVariant,
    outline = StardewOutline
)

@Composable
fun StardewTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent Stardew brand aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    StardewTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
