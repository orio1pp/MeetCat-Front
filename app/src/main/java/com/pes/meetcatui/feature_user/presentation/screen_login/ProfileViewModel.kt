package com.pes.meetcatui.feature_user.presentation.screen_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.SavedPreference
import com.pes.meetcatui.feature_user.domain.DataRepositoryUsers
import kotlinx.coroutines.launch

class ProfileViewModel(
    val dataRepository: DataRepositoryUsers
) : ViewModel() {

    fun tancarCompte() {
        viewModelScope.launch {
            val user = dataRepository.getCurrentUser()
            if (user != null) {
                user.id?.let { dataRepository.removeAccount(it) }
            }
        }
    }
}