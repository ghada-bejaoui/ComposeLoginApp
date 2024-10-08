package com.example.composeloginapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import com.example.composeloginapp.ui.screens.LoginScreen
import com.example.composeloginapp.ui.screens.SignupScreen

enum class ScreenState {
    LOGIN,
    SIGNUP
}

@Composable
fun LoginSignupNavigation() {
    var currentScreen by remember { mutableStateOf(ScreenState.LOGIN) }

    when (currentScreen) {
        ScreenState.LOGIN -> LoginScreen(
            onSignupClick = { currentScreen = ScreenState.SIGNUP }
        )
        ScreenState.SIGNUP -> SignupScreen(
            onLoginClick = { currentScreen = ScreenState.LOGIN }
        )
    }
}
