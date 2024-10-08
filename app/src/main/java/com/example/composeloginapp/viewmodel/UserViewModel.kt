package com.example.composeloginapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeloginapp.data.UserDao
import com.example.composeloginapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserViewModel(private val userDao: UserDao) : ViewModel() {

    fun signup(
        username: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            onError("All fields must be filled.")
            return
        }
        if (password.length < 8) {
            onError("Password must be at least 8 characters long.")
            return
        }
        if (password != confirmPassword) {
            onError("Passwords do not match.")
            return
        }

        viewModelScope.launch {
            val user = User(username = username, password = password)
            withContext(Dispatchers.IO) {
                userDao.insert(user)
            }
            onSuccess()
        }
    }

    var errorMessage: String = ""
    var loading: Boolean = false

    fun login(username: String,
              password: String,
        onLoginResult: (Boolean) -> Unit,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        loading = true

        viewModelScope.launch {
            delay(2000) // Simule un temps de chargement pour la connexion

            // Vérification si les champs sont vides
            if (username.isBlank() || password.isBlank()) {
                errorMessage = "Username and password cannot be empty."
                loading = false
                onError(errorMessage)
                onLoginResult(false)
                return@launch
            }

            // Validation du nom d'utilisateur et mot de passe
            val user = withContext(Dispatchers.IO) {
                userDao.getUser(username)
            }

            if (user != null && user.password == password) {
                errorMessage = "Login successful!"
                loading = false
                onSuccess(errorMessage)
                onLoginResult(true)
            } else {
                errorMessage = "Invalid username or password."
                loading = false
                onError(errorMessage)
                onLoginResult(false)
            }
        }
    }

}