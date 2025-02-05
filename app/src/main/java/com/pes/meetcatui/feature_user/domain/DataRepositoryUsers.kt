package com.pes.meetcatui.feature_user.domain

import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.network.Friendships.FriendshipData
import com.pes.meetcatui.network.UserData
import retrofit2.Response
import kotlinx.coroutines.flow.Flow


interface DataRepositoryUsers {
    //suspend fun createUser(user : UserData)
    suspend fun login(username : String, password : String) : Boolean
    suspend fun getUser(username: String) : UserData?
    suspend fun postUser(user: UserData) : Response<UserData>
    suspend fun addFriend(username: String): FriendshipData?
    suspend fun removeFriend(username: String): FriendshipData?
    suspend fun getFriend(): List<FriendshipData>?
    suspend fun removeAccount(id: Long): Response<UserData>
    suspend fun getCurrentUser(): UserData?
    suspend fun getUserByAuth(): Flow<Resource<UserData>>
    suspend fun update(user: UserData) : Response<UserData>
}