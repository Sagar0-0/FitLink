package com.example.fitlink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.fitlink.ui.theme.FitLinkTheme
import com.example.fitlink.ui.view.Login
import com.example.fitlink.ui.view.MainScreenView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLinkTheme {
                if (viewModel.accessToken.isBlank()) {
                    Login(this)
                } else {
                    MainScreenView(this)
                }
            }
        }
    }
}