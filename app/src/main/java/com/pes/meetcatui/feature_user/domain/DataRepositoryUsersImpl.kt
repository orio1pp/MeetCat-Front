package com.pes.meetcatui.feature_user.domain

import com.pes.meetcatui.feature_user.data.DataPreferences
import com.pes.meetcatui.network.MeetCatApi
import com.pes.meetcatui.network.RoleData
import com.pes.meetcatui.network.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONArray
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                meetCatApi.getUser(username).body()?.let { dataPreferences.setUser(it) }
                return true
            }
            return false
        } catch (e: Exception) {
            println("Exception: " + e.message)
        }
        return false
    }

    override suspend fun getUser(username: String): UserData? {
        val user = meetCatApi.getUser(username).body()
        return user
    }

    private fun transformToToken(token: Response<UserToken>): UserToken {
        return UserToken(
            token.body()?.access_token ?: "",
            token.body()?.refresh_token ?: ""
        )
    }

    override suspend fun addFriend(username: String): UserData? {
        //val accessToken : String = dataPreferences.getAccessToken().collect().toString()
        val accessToken : String = "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhQGdtYWlsLmNvbSIsInJvbGVzIjpbXSwiaXNzIjoiaHR0cDovLzEwLjQuNDEuNDk6ODA4MC9sb2dpbiIsImV4cCI6MTY3MDMzMjA3MH0.pGBLZ6B2Jdbm9J__iUc5aa6tyXQlo3-b0v1BZ07x3WU"
        val user = meetCatApi.addFriend(username, accessToken).body()
        return user
    }

    suspend fun downloadData() {
        /*descarreguem dades que necessitem*/
    }
}