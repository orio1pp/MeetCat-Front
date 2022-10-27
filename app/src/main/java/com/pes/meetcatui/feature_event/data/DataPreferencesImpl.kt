package com.pes.meetcatui.feature_event.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val EVENT_LIST_KEY = "EVENT_LIST_KEY"

class DataPreferencesImpl (
    private val dataStore: DataStore<Preferences>,
        ) : DataPreferences{

    override fun getEventList(): Flow<List<Event>> = dataStore.getJsonOrDefault(EVENT_LIST_KEY, emptyList())

    //override fun getEvent(id: Number): Flow<Event> = dataStore.getJsonOrDefault()

    override suspend fun setEventList(eventList: List<Event>) {
        dataStore.setJson(EVENT_LIST_KEY, eventList)
    }

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

