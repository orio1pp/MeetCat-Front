package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.data.DataPreferences
import com.pes.meetcatui.network.EventDetailsData
import com.pes.meetcatui.network.EventsData
import com.pes.meetcatui.network.MeetCatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class DataRepositoryImpl (
    appScope: CoroutineScope,
    private val meetcatApi: MeetCatApi,
    private val dataPreferences: DataPreferences,
) : DataRepository {
    /*
    init {
        appScope.launch {
            downloadData()
        }
    }*/

    //private val eventList = dataPreferences.getEventList()

    override fun getEvents(pageNum:Int): Flow<Resource<EventPage>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getEvents(pageNum,20)
            if (apiResponse.isSuccessful) {
                val result = buildEventList(apiResponse.body()!!)

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

    override fun getAllEvents(): Flow<Resource<EventPage>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getEvents(0, null)
            if (apiResponse.isSuccessful) {
                val result = buildEventList(apiResponse.body()!!)

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

    override suspend fun createEvent( event: Event) : String {
        try {
            val eventSerial = EventDetailsData(event.eventId, event.name, event.subtitle, event.description, event.startDate, event.endDate, event.link, event.placeName, event.location, event.address)
            println(eventSerial)
            meetcatApi.createEvent(eventSerial);
            return ("Api is successful")
        } catch (e: IOException) {
            return ("IO Exception: ${e.message}")
        } catch (e: TimeoutException) {
            return ("Timeout Exception: ${e.message}")
        } catch (e: HttpException) {
            return ("Http Exception: ${e.message}")
        }
    }

    /*
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
    }*/

    //override fun getEventList(): Flow<List<Event>> = eventList

    private fun buildEventList(
        eventsData: EventsData
    ) : EventPage {
        val eventList = mutableListOf<Event>()
        for (event in eventsData.events)
        {
            eventList.add(buildEvent(event))
        }
        val eventPage = EventPage(eventList, eventsData.page!!)
        return(eventPage)
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
        location = eventData.location,
        placeName = eventData.placeName,
        link = eventData.link,
        address = eventData.address,
    )
}



