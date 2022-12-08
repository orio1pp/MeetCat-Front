package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.feature_event.Resource /* canviar aixooooo*/
import com.pes.meetcatui.network.ChatApi
import com.pes.meetcatui.network.ChatDetailsResponse
import com.pes.meetcatui.network.EventDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class DataRepositoryImpl(
    appScope: CoroutineScope,
    private val chatApi: ChatApi,
) : DataRepository {

    init {
        appScope.launch {
            downloadData()
        }
    }


    override fun getChat(chatId: Int): Flow<Resource<Chat>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = chatApi.getChatData(chatId)
            if (apiResponse.isSuccessful) {
                val result = buildChat(apiResponse.body()!!)
                emit(Resource.Success(result))
            } else {
                emit(Resource.Error("Api is unsuccessful"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }
    }

    override suspend fun downloadData() {
        val chat = getChatsData()
    }

    private suspend fun getChatsData(): List<ChatDetailsResponse> {
        try {
            return chatApi.getAllChats()
        } catch (e: Exception) {
            return emptyList()
        }
    }

    private fun buildChat(
        chatData: ChatDetailsResponse,
    ) = Chat(
        chatId = chatData.chatId,
        username = chatData.username,
        messages = chatData.messages,
    )

    private fun buildChatList(
        chatListData: List<ChatDetailsResponse>
    ) : List<Chat> {
        val result = mutableListOf<Chat>()
        for (chat in chatListData)
        {
            result.add(buildChat(chat))
        }
        return(result)
    }
}