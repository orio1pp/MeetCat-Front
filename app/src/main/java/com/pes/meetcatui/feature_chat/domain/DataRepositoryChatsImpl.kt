package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.feature_user.data.DataPreferences
import com.pes.meetcatui.network.MeetCatApi
import com.pes.meetcatui.network.chat.ChatData
import com.pes.meetcatui.network.chat.GetChatData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class DataRepositoryChatsImpl(
    val appScope: CoroutineScope,
    val meetCatApi: MeetCatApi,
    val dataPreferences: DataPreferences,
) : DataRepositoryChats {

    override fun getChatByUser(): Flow<GetChatData> {
        TODO("Not yet implemented")
    }


}