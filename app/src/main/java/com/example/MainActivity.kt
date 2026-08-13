package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.data.database.StardewDatabase
import com.example.data.repository.StardewRepository
import com.example.ui.screens.MainScreen
import com.example.ui.theme.StardewTheme
import com.example.ui.viewmodel.StardewViewModel
import com.example.ui.viewmodel.StardewViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy { StardewDatabase.getDatabase(this, lifecycleScope) }
    private val repository by lazy { StardewRepository(database.itemDao(), database.favoriteDao()) }
    private val viewModel: StardewViewModel by viewModels {
        StardewViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StardewTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
