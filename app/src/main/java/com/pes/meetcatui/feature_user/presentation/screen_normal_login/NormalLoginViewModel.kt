package com.pes.meetcatui.feature_user.presentation.screen_normal_login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsers
import kotlinx.coroutines.launch

class NormalLoginViewModel(
    val dataRepo: DataRepositoryUsers
) : ViewModel() {

    private val _warning = mutableStateOf("")
    val warning: State<String> = _warning

    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (username.isEmpty() || password.isEmpty()) {
                _warning.value = "El nom d'usuari o la contrassenya son buïts."
            } else {
                val loggedIn = authenticate(username = username, password = password)
                if (!loggedIn) {
                    _warning.value = "El nom d'usuari o la contrassenya son incorrectes."
                } else {
                    /* falta passar a la seg pantalla */
                }
            }
        }
    }

    private suspend fun authenticate(username: String, password: String): Boolean {
        return dataRepo.login(username, password)
    }

}