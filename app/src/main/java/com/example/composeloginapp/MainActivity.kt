package com.example.composeloginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composeloginapp.ui.LoginSignupNavigation
import com.example.composeloginapp.ui.theme.ComposeLoginAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLoginAppTheme {
                LoginSignupNavigation()
            }
        }
    }
}
