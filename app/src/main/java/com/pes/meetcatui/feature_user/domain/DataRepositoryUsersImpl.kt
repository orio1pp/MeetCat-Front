package com.pes.meetcatui.feature_user.domain

import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.data.DataPreferences
import com.pes.meetcatui.network.Friendships.FriendshipData
import com.pes.meetcatui.network.MeetCatApi
import com.pes.meetcatui.network.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
            //downloadData()
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

    override suspend fun getCurrentUser(): UserData? {
        var accessToken: String = "Bearer "
        var username: String
        runBlocking(Dispatchers.IO) {
            accessToken += dataPreferences.getAccessToken().first()
            username = dataPreferences.getUser().first()
        }

        val user = meetCatApi.getUser(username, accessToken).body()
        return user
    }

    override suspend fun getUserByAuth(): Flow<Resource<UserData>> = flow {
        try {
            var accessToken: String = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            emit(Resource.Loading())
            val userResponse = meetCatApi.getUserByAuth(accessToken)
            if (userResponse.isSuccessful) {
                emit(Resource.Success(userResponse.body()!!))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override suspend fun postUser(user: UserData) : Response<UserData>{
        val user = meetCatApi.postUser(user)
        return user
    }

    override suspend fun update(user: UserData) : Response<UserData>{
        var accessToken = "Bearer "
        runBlocking(Dispatchers.IO) {
            accessToken += dataPreferences.getAccessToken().first()
        }
        return meetCatApi.updateUser(user.id!!, user, accessToken)
    }

    override suspend fun removeAccount(id: Long): Response<UserData> {
        val user = meetCatApi.removeAccount(id)
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
            val friendship = meetCatApi.addFriend(
                FriendshipData(id = 30, friendId = username, ownerId = ""),
                accessToken
            ).body()
            return friendship
        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }

    override suspend fun getFriend(): List<FriendshipData>? {
        var accessToken: String = "Bearer "
        runBlocking(Dispatchers.IO) {
            accessToken += dataPreferences.getAccessToken().first()
        }
        try {
            val friendship = meetCatApi.getFriend(0, 10, accessToken)?.body()
            return friendship
        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }

    override suspend fun removeFriend(username: String): FriendshipData? {
        var accessToken: String = "Bearer "
        runBlocking(Dispatchers.IO) {
            accessToken += dataPreferences.getAccessToken().first()
        }
        try {
            val friendship = meetCatApi.removeFriend(username, accessToken).body()
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
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiQGdtYWlsLmNvbSIsInJvbGVzIjpbXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjcwNjc1NzcxfQ.J2CiKF6o7oCs86OpFxaV6KcNirI7nlCABdJzIlTeGFc",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhQGdtYWlsLmNvbSIsImlzcyI6Imh0dHA6Ly8xMC40LjQxLjQ5OjgwODAvbG9naW4iLCJleHAiOjE2NzA1MjExNDh9.Z622pp9XOYiXkc4MwkbtvvUxKvhjd-AIrsyp_SPfeio"
            )
        )
    }
}