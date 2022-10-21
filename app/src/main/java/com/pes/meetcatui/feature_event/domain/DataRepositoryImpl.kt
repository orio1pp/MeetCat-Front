package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.network.EventDetailsData
import com.pes.meetcatui.network.MeetCatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName

class DataRepositoryImpl (
    appScope: CoroutineScope,
    private val meetcatApi: MeetCatApi,
) : DataRepository {

    init {
        appScope.launch { }
    }

    override fun getEvent(eventId: Int): Flow<Event> = flow {
        emit(buildEvent(getEventDetails(eventId)))
    }

    private suspend fun getEventDetails(eventId: Int) = try {
        meetcatApi.getEventData(eventId)
    } catch (e: Exception) {
        throw e
    }

    private fun buildEvent(
        eventData: EventDetailsData,
    ) = Event(
        eventId = eventData.eventId,
        name = eventData.name,
        subtitle = eventData.subtitle,
        description = eventData.description,
        startDate = eventData.startDate,
        endDate = eventData.endDate,
        locationName = eventData.locationName,
        address = eventData.address,
        link = eventData.link
    )
}

