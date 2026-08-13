package com.example.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.navigation.Screen
import com.example.ui.viewmodel.StardewViewModel

@Composable
fun MainScreen(
    viewModel: StardewViewModel,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Directory) }

    val screens = listOf(
        Screen.Directory,
        Screen.Compendium,
        Screen.Calculator,
        Screen.Calendar,
        Screen.Recommendations,
        Screen.Favorites
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                tonalElevation = 8.dp
            ) {
                screens.forEach { screen ->
                    val isSelected = currentScreen.route == screen.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentScreen = screen },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                                contentDescription = screen.title
                            )
                        },
                        label = {
                            Text(
                                text = screen.title,
                                fontSize = 10.sp,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        },
                        modifier = Modifier.testTag("navigation_item_${screen.route}")
                    )
                }
            }
        }
    ) { innerPadding ->
        BoxModifierPadding(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                is Screen.Directory -> DirectoryScreen(
                    viewModel = viewModel,
                    onNavigateToCalculatorWithCrop = {
                        currentScreen = Screen.Calculator
                    }
                )
                is Screen.Compendium -> CompendiumScreen(viewModel = viewModel)
                is Screen.Calculator -> CalculatorScreen(viewModel = viewModel)
                is Screen.Calendar -> CalendarScreen(viewModel = viewModel)
                is Screen.Recommendations -> RecommendationsScreen()
                is Screen.Favorites -> FavoritesScreen(
                    viewModel = viewModel,
                    onNavigateToCalculatorWithCrop = {
                        currentScreen = Screen.Calculator
                    }
                )
            }
        }
    }
}

@Composable
private fun BoxModifierPadding(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Box(modifier = modifier) {
        content()
    }
}
