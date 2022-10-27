package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.data.DataPreferences
import com.pes.meetcatui.network.EventDetailsData
import com.pes.meetcatui.network.MeetCatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class DataRepositoryImpl (
    appScope: CoroutineScope,
    private val meetcatApi: MeetCatApi,
    private val dataPreferences: DataPreferences,
) : DataRepository {

    init {
        appScope.launch {
            downloadData()
        }
    }

    private val eventList = dataPreferences.getEventList()

    override fun getEvent(eventId: Int): Flow<Resource<Event>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getEventData(eventId)
            if (apiResponse.isSuccessful) {
                val result = buildEvent(apiResponse.body()!!)
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
        val events = getEventsData()

        dataPreferences.setEventList(buildEventList(events))
    }

    private suspend fun getEventsData(): List<EventDetailsData> {
        try {
            return meetcatApi.getAllEvents()
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override fun getEventList(): Flow<List<Event>> = eventList

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

    private fun buildEventList(
        eventListData: List<EventDetailsData>
    ) : List<Event> {
        val result = mutableListOf<Event>()
        for (event in eventListData)
        {
            result.add(buildEvent(event))
        }
        return(result)
    }

}



