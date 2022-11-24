package com.pes.meetcatui.feature_user.domain

import com.pes.meetcatui.feature_user.data.DataPreferences
import com.pes.meetcatui.network.MeetCatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class DataRepositoryUsersImpl(
    val appScope: CoroutineScope,
    val meetCatApi: MeetCatApi,
    val dataPreferences: DataPreferences,
) : DataRepositoryUsers {
    init {
        appScope.launch {
            dataPreferences.setToken(UserToken("a", "b"))
            downloadData()
        }
    }

    override suspend fun login(username: String, password: String): UserToken {
        try {
            val userToken = transformToToken(meetCatApi.login(username, password))
            dataPreferences.setToken(userToken)
            return userToken
        } catch (e: Exception) {
            println("Exception: " + e.message)
        }
        return UserToken("", "")
    }

    private fun transformToToken(token: Response<UserToken>): UserToken {
        return UserToken(
            token.body()?.access_token ?: "",
            token.body()?.refresh_token ?: ""
        )
    }

    suspend fun downloadData() {
        println(dataPreferences.getAccessToken())
        println(dataPreferences.getRefreshToken())
    }
}