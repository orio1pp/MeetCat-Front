package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.feature_user.data.DataPreferences
import com.pes.meetcatui.network.MeetCatApi
import com.pes.meetcatui.network.chat.GetChatData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DataRepositoryChatsImpl(
    val appScope: CoroutineScope,
    val meetCatApi: MeetCatApi,
    val dataPreferences: DataPreferences,
) : DataRepositoryChats {

    init {
        appScope.launch {
            //downloadData()
        }
    }

    override suspend fun getChatByUser(page: Int): List<GetChatData>? {
        var accessToken = "Bearer "
        //appScope.launch {
        //withContext(Dispatchers.IO) {
        runBlocking(Dispatchers.IO) {
            accessToken += dataPreferences.getAccessToken().first()
        }
        //}
        return meetCatApi.getChatByUser(accessToken).body()
    }

    suspend fun downloadData() {
        val token = meetCatApi.login("b@gmail.com", "b").body()
        dataPreferences.setToken(token!!)
    }
}