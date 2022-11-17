package com.pes.meetcatui.feature_chat.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pes.meetcatui.feature_chat.domain.Chat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val CHAT_LIST_KEY = "CHAT_LIST_KEY"

class DataPreferencesImpl(
    private val dataStore: DataStore<Preferences>,
) : DataPreferences {

    override fun getChatList(): Flow<List<Chat>> =
        dataStore.getJsonOrDefault(CHAT_LIST_KEY, emptyList())

    override suspend fun setChatList(chatList: List<Chat>) {
        dataStore.setJson(CHAT_LIST_KEY, chatList)
    }
}


private inline fun <reified T> DataStore<Preferences>.getJsonOrDefault(
    key: String,
    default: T
) =
    data.map { preferences ->
        preferences[stringPreferencesKey(key)]?.let { encoded ->
            Json.decodeFromString<T>(encoded)
        } ?: default
    }

private suspend inline fun <reified T> DataStore<Preferences>.setJson(key: String, value: T) =
    edit { preferences ->
        preferences[stringPreferencesKey(key)] = Json.encodeToString(value)
    }