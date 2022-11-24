package com.pes.meetcatui.feature_user.domain

import com.pes.meetcatui.network.MeetCatApi
import com.pes.meetcatui.network.UserData
import retrofit2.Response

class DataRepositoryUsersImpl(private val meetCatApi: MeetCatApi) : DataRepositoryUsers {

    override suspend fun login(username : String, password : String): UserToken {
        try {
            return transformToToken(meetCatApi.login(username, password))
        } catch (e: Exception) {
            println("Exception: " + e.message)
        }
        return UserToken("", "")
    }

    private fun transformToToken(token: Response<UserToken>): UserToken {
        return UserToken(token.body()?.access_token ?: "",
            token.body()?.refresh_token ?: "")
    }

}