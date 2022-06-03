package com.shanu.notes_app.ui.app_info

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppInfoScreen(
    viewModel: AppInfoViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar {

            }
        }
    ) {
        Text(text = "App is developed by Galanteria")
    }
}