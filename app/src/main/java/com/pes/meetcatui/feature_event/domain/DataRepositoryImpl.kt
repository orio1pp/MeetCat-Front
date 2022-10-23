package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.Resource
import com.pes.meetcatui.network.EventDetailsData
import com.pes.meetcatui.network.MeetCatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class DataRepositoryImpl (
    appScope: CoroutineScope,
    private val meetcatApi: MeetCatApi,
) : DataRepository {

    init {
        appScope.launch { }
    }

    override fun getEvent(eventId: Int): Flow<Resource<Event>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getEventData(eventId)
            if (apiResponse.isSuccessful) {
                val result = apiResponse.body() as Event
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

