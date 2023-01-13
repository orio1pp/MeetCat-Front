package com.pes.meetcatui.feature_user.presentation.screen_login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.SavedPreference
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.EventScreenState
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsers
import com.pes.meetcatui.network.UserData
import kotlinx.coroutines.launch

class ProfileViewModel(
    val dataRepository: DataRepositoryUsers,
) : ViewModel() {
    val user : MutableState<UserData?> = mutableStateOf(null)
    val loading = mutableStateOf(true)

    init {
        viewModelScope.launch {
            dataRepository.getUserByAuth().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        user.value = resource.data!!
                        loading.value = false
                    }
                    is Resource.Error -> {
                        user.value = UserData(
                            id = null,
                            username = "",
                            password = "",
                            roles = emptyList(),
                            about = "",
                            createdDate = null,
                            lastUpdate = null,
                        )
                        loading.value = false
                    }
                    is Resource.Loading -> {
                        loading.value = true
                    }
                }
            }
        }
    }

    fun updateBio(newBio: String) {
        user.value!!.about = newBio
        viewModelScope.launch {
            dataRepository.update(user.value!!)
        }
    }

    fun tancarCompte() {
        viewModelScope.launch {
            val user = dataRepository.getCurrentUser()
            if (user != null) {
                user.id?.let { dataRepository.removeAccount(it) }
            }
        }
    }
}