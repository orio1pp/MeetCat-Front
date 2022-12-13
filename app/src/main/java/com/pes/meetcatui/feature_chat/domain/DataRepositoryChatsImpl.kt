package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.feature_user.data.DataPreferences
import com.pes.meetcatui.network.MeetCatApi
import com.pes.meetcatui.network.chat.GetChatData
import com.pes.meetcatui.network.chat.MessageData
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
        return meetCatApi.getChatsByUser(accessToken).body()
    }

    override suspend fun getMessagesByChat(chatId: Long, page: Int): List<MessageData>? {
        var accessToken = "Bearer "
        runBlocking(Dispatchers.IO) {
            accessToken += dataPreferences.getAccessToken().first()
        }
        try {
            return meetCatApi.getMessagesByChat(chatId, page, 100, accessToken).body()
        }
        catch (e : Exception) {
            println(e.message)
        }
        return null
    }

    override suspend fun newMessage(messageData: MessageData): Boolean {
        var accessToken = "Bearer "
        runBlocking(Dispatchers.IO) {
            accessToken += dataPreferences.getAccessToken().first()
        }
        try {
            meetCatApi.postMessage(messageData, accessToken)
            return true
        }
        catch (e : Exception) {
            println(e.message)
            return false
        }
    }

    override suspend fun getUsername(): String {
        var username = dataPreferences.getUser().first()
        return username
    }


    suspend fun downloadData() {

    }
}