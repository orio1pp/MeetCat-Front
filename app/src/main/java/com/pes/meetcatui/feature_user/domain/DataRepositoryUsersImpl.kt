package com.pes.meetcatui.feature_user.domain

import com.pes.meetcatui.feature_user.data.DataPreferences
import com.pes.meetcatui.network.FriendshipData
import com.pes.meetcatui.network.MeetCatApi
import com.pes.meetcatui.network.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class DataRepositoryUsersImpl(
    val appScope: CoroutineScope,
    val meetCatApi: MeetCatApi,
    val dataPreferences: DataPreferences,
) : DataRepositoryUsers {
    init {
        appScope.launch {
            downloadData()
        }
    }

    override suspend fun login(username: String, password: String): Boolean {
        try {
            val userToken = transformToToken(meetCatApi.login(username, password))
            if (userToken.access_token.isNotEmpty()) {
                dataPreferences.setToken(userToken)
                val accessToken: String = "Bearer " + userToken.access_token
                meetCatApi.getUser(username, accessToken).body()
                    ?.let { dataPreferences.setUser(it) }
                return true
            }
            return false
        } catch (e: Exception) {
            println("Exception: " + e.message)
        }
        return false
    }

    override suspend fun getUser(username: String): UserData? {
        var accessToken: String = "Bearer "
        runBlocking(Dispatchers.IO) {
            accessToken += dataPreferences.getAccessToken().first()
        }
        val user = meetCatApi.getUser(username, accessToken).body()
        return user
    }

    private fun transformToToken(token: Response<UserToken>): UserToken {
        return UserToken(
            token.body()?.access_token ?: "",
            token.body()?.refresh_token ?: ""
        )
    }

    override suspend fun addFriend(username: String): FriendshipData? {
        var accessToken: String = "Bearer "
        runBlocking(Dispatchers.IO) {
            accessToken += dataPreferences.getAccessToken().first()
        }
        try {
            val friendship = meetCatApi.addFriend(username, accessToken).body()
            println(friendship)
            return friendship
        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }

    suspend fun downloadData() {
        /*descarreguem dades que necessitem*/
        dataPreferences.setToken(
            UserToken(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhQGdtYWlsLmNvbSIsInJvbGVzIjpbXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjcwNTM2NzgwfQ.lcHgftwdN-47qxVgsTsdZdoy4LTZ2RD5ySy0zclKTCI",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhQGdtYWlsLmNvbSIsImlzcyI6Imh0dHA6Ly8xMC40LjQxLjQ5OjgwODAvbG9naW4iLCJleHAiOjE2NzA1MjExNDh9.Z622pp9XOYiXkc4MwkbtvvUxKvhjd-AIrsyp_SPfeio"
            )
        )
    }
}