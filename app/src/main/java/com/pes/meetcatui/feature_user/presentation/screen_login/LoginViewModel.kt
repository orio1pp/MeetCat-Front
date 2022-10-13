package com.pes.meetcatui.feature_user.presentation.screen_login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _warning = mutableStateOf("")
    val warning: State<String> = _warning

    fun Login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            _warning.value = "El nom d'usuari o la contrassenya son buïts."
            return;
        }
        if (!authenticate(username = username, password = password)) {
            _warning.value = "El nom d'usuari o la contrassenya son incorrectes."
            return;
        }
        //else navegar a la seguent pantalla
    }

    private fun authenticate(username: String, password: String): Boolean {
        return (username == "user" && password == "password")
    }

}