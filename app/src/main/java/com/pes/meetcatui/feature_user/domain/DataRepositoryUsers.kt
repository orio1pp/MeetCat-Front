package com.pes.meetcatui.feature_user.domain

import com.pes.meetcatui.network.FriendshipData
import com.pes.meetcatui.network.UserData
import retrofit2.Response


interface DataRepositoryUsers {
    //suspend fun createUser(user : UserData)
    suspend fun login(username : String, password : String) : Boolean
    suspend fun getUser(username: String) : UserData?
    suspend fun addFriend(username: String): FriendshipData?
}