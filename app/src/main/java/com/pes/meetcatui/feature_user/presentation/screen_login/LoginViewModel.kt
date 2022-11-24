package com.pes.meetcatui.feature_user.presentation.screen_login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsers
import com.pes.meetcatui.feature_user.domain.UserToken
import kotlinx.coroutines.launch

class LoginViewModel(
    val dataRepo: DataRepositoryUsers
) : ViewModel() {
    private val _warning = mutableStateOf("")
    val warning: State<String> = _warning

    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (username.isEmpty() || password.isEmpty()) {
                _warning.value = "El nom d'usuari o la contrassenya son buïts."
            } else {
                val token = authenticate(username = username, password = password)
                if (token.access_token.isEmpty() || token.refresh_token.isEmpty()) {
                    _warning.value = "El nom d'usuari o la contrassenya son incorrectes."
                } else {
                    println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                }
            }
        }
    }

    private suspend fun authenticate(username: String, password: String): UserToken {
        val token: UserToken = dataRepo.login(username, password)
        return token
    }

}