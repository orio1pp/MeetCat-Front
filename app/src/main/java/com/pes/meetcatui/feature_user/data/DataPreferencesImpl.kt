package com.pes.meetcatui.feature_user.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_user.domain.UserToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val EVENT_LIST_KEY = "EVENT_LIST_KEY"
private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"

class DataPreferencesImpl(
    private val dataStore: DataStore<Preferences>,
) : DataPreferences {

    override fun getEventList(): Flow<List<Event>> =
        dataStore.getJsonOrDefault(EVENT_LIST_KEY, emptyList())

    //override fun getEvent(id: Number): Flow<Event> = dataStore.getJsonOrDefault()

    override suspend fun setEventList(eventList: List<Event>) {
        dataStore.setJson(EVENT_LIST_KEY, eventList)
    }

    override suspend fun setToken(token: UserToken) {
        dataStore.setJson(ACCESS_TOKEN_KEY, token.access_token)
        dataStore.setJson(REFRESH_TOKEN_KEY, token.refresh_token)
    }

    /*
    override fun getAccessToken(): String {
        val access_token : Flow<String> = dataStore.getJsonOrDefault(ACCESS_TOKEN_KEY, "")
        println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN")


        var list : MutableList<String> = mutableListOf("")
        println(access_token)
        access_token.collect { list.add("$it"[0].toString()) }
        println(list)


        return access_token
    }


    override suspend fun getRefreshToken(): String {
        val refresh_token = dataStore.getJsonOrDefault(REFRESH_TOKEN_KEY, "")
        println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN")
        return refresh_token
    }*/
    override fun getAccessToken(): Flow<String> = dataStore.getJsonOrDefault(ACCESS_TOKEN_KEY, "")
    override fun getRefreshToken(): Flow<String> = dataStore.getJsonOrDefault(REFRESH_TOKEN_KEY, "")

}

private inline fun <reified T> DataStore<Preferences>.getJsonOrDefault(key: String, default: T) =
    data.map { preferences ->
        preferences[stringPreferencesKey(key)]?.let { encoded ->
            Json.decodeFromString<T>(encoded)
        } ?: default
    }

private suspend inline fun <reified T> DataStore<Preferences>.setJson(key: String, value: T) =
    edit { preferences ->
        preferences[stringPreferencesKey(key)] = Json.encodeToString(value)
    }

