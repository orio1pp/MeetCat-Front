package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.common.Resource
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getChat(chatId: Int): Flow<Resource<Chat>>
    //fun getChatList(): Flow<List<Chat>>
    suspend fun downloadData()
}