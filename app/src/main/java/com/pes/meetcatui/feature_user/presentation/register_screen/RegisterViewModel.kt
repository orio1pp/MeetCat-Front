package com.pes.meetcatui.feature_user.presentation.register_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsers
import com.pes.meetcatui.network.UserData
import kotlinx.coroutines.launch
import com.pes.meetcatui.network.MeetCatApi

class RegisterViewModel(
    val dataRepository: DataRepositoryUsers
) : ViewModel() {
    private val _warning = mutableStateOf("")
    val warning: State<String> = _warning


    fun register(username: String, password: String) {
        viewModelScope.launch {
            if (username.isEmpty() || password.isEmpty()) {
                _warning.value = "El nom d'usuari o la contrassenya son buïts."
            } else {
                val user = UserData(null, username, password, emptyList(), null, null, null)
                dataRepository.postUser(user)
            }
            //navegar a login
        }
    }

    private suspend fun authenticate(username: String, password: String): Boolean {
        return dataRepository.login(username, password)
    }

}
