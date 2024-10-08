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
import com.example.composeloginapp.data.AppDatabase
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeloginapp.viewmodel.UserViewModel
import com.example.composeloginapp.viewmodel.UserViewModelFactory


@Composable
fun SignupScreen(onLoginClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val userDao = database.userDao()
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(userDao)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Signup", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation =  PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Appel au ViewModel pour l'inscription
                userViewModel.signup(
                    username,
                    password,
                    confirmPassword,
                    onSuccess = {
                        successMessage = "Signup successful!"
                        errorMessage = ""
                        // Effacer les champs du formulaire après un succès
                        username = ""
                        password = ""
                        confirmPassword = ""
                    },
                    onError = { error ->
                        errorMessage = error
                        successMessage = ""
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Signup")
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

        TextButton(onClick = onLoginClick) {
            Text("Already have an account? Log in here!")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(onLoginClick = {})
}
