package com.example.composeloginapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeloginapp.data.AppDatabase
import com.example.composeloginapp.viewmodel.UserViewModel
import com.example.composeloginapp.viewmodel.UserViewModelFactory

@Composable
fun LoginScreen(onSignupClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val userDao = database.userDao()
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(userDao)
    )
    var successMessage by remember { mutableStateOf("") }  // Message de succès

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Login", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                userViewModel.errorMessage = ""
            },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                userViewModel.errorMessage = ""
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                userViewModel.login(username, password,
                    onLoginResult = { success ->
                        if (success) {
                            // Connexion réussie, ici vous pouvez rediriger l'utilisateur
                        } else {
                            // La connexion a échoué
                        }
                    },
                    onError = { error ->
                        successMessage = "" // Réinitialisez le message de succès
                        errorMessage = error // Affichez le message d'erreur
                    },
                    onSuccess = { successMsg ->
                        successMessage = successMsg // Affichez le message de succès
                        errorMessage = "" // Réinitialisez le message d'erreur
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !userViewModel.loading
        ) {
            if (userViewModel.loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Login")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Affichage des messages d'erreur
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }
        // Affichage des messages de succes
        if (successMessage.isNotEmpty()) {
            Text(
                text = successMessage,
                color = MaterialTheme.colorScheme.secondary
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSignupClick) {
            Text("Don't have an account? Sign up here!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onSignupClick = {})
}
