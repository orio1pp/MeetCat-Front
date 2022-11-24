package com.pes.meetcatui.feature_user.domain

import com.pes.meetcatui.network.UserData
import retrofit2.Response


interface DataRepositoryUsers {
    //suspend fun createUser(user : UserData)
    suspend fun login(username : String, password : String) : UserToken
}