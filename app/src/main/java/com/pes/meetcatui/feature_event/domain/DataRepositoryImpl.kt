package com.pes.meetcatui.feature_event.domain
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.data.DataPreferences
import com.pes.meetcatui.network.*
import kotlinx.coroutines.Dispatchers

import com.pes.meetcatui.network.EventDetailsData
import com.pes.meetcatui.network.EventsData
import com.pes.meetcatui.network.MeetCatApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class DataRepositoryImpl (
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

    override fun getEvents(pageNum:Int, title:String?): Flow<Resource<EventPage>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getEventsWithTitle(pageNum, 20, title)
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

    override fun getReportedEvents(pageNum: Int, title:String?) : Flow<Resource<EventPage>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getReportedEventsWithTitle(pageNum, 20, title)
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
            val eventSerial = buildEventDetailsData(event)
            println(eventSerial)

            var accessToken: String = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            meetcatApi.createEvent(eventSerial, accessToken)
            return ("Api is successful")
        } catch (e: IOException) {
            return ("IO Exception: ${e.message}")
        } catch (e: TimeoutException) {
            return ("Timeout Exception: ${e.message}")
        } catch (e: HttpException) {
            return ("Http Exception: ${e.message}")
        }
    }

    override fun getNearestEvents(latitude: Double,longitude: Double,distance: Double): Flow<Resource<EventPage>> = flow{
        try {
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getNearestEvents(latitude, longitude, distance)
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


    override fun getAttendance(eventId: Long): Flow<Resource<Boolean>> = flow {
        try {
            var accessToken: String = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            emit(Resource.Loading())
            val attendanceResponse = meetcatApi.getAttendance(eventId, accessToken)
            if (attendanceResponse.isSuccessful) {
                emit(Resource.Success(attendanceResponse.body()!!))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }
    }

    override suspend fun createAttendance(eventId: Long): Flow<Resource<Long>> = flow {
        try {
            var accessToken: String = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            emit(Resource.Loading())
            val attendanceResponse = meetcatApi.createAttendance(AttendanceData(eventId), accessToken)
            if (attendanceResponse.isSuccessful) {
                emit(Resource.Success(attendanceResponse.body()!!.eventId))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }
    }

    override suspend fun deleteAttendance(eventId: Long): Flow<Resource<Long>> = flow {
        try {
            var accessToken: String = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            emit(Resource.Loading())
            val attendanceResponse = meetcatApi.deleteAttendance(eventId, accessToken)
            if (attendanceResponse.isSuccessful) {
                emit(Resource.Success(attendanceResponse.body()!!.eventId))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }
    }

    override suspend fun deleteEvent(eventId: Long): Flow<Resource<Unit>> = flow {
        try {
            var accessToken = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            emit(Resource.Loading())
            val response = meetcatApi.deleteEvent(eventId, accessToken)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }
    }

    override suspend fun getUsername(): Flow<Resource<String>> = flow {
        try {
            var accessToken = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            emit(Resource.Loading())
            val userResponse = meetcatApi.getUserByAuth(accessToken)
            if (userResponse.isSuccessful) {
                emit(Resource.Success(userResponse.body()!!.username))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }
    }

    override suspend fun reportEvent(event: Event): String {
        try {
            meetcatApi.reportEvent(event.eventId);
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
        username = eventData.username,
        description = eventData.description,
        startDate = eventData.startDate,
        endDate = eventData.endDate,
        location = eventData.location,
        placeName = eventData.placeName,
        link = eventData.link,
        address = eventData.address,
        attendeesCount = eventData.attendeesCount,
    )

    private fun buildEventDetailsData(
        eventData: Event,
    ) = EventDetailsData(
        eventId = eventData.eventId,
        name = eventData.name,
        subtitle = eventData.subtitle,
        username = eventData.username,
        description = eventData.description,
        startDate = eventData.startDate,
        endDate = eventData.endDate,
        location = eventData.location,
        placeName = eventData.placeName,
        link = eventData.link,
        address = eventData.address,
        attendeesCount = eventData.attendeesCount,
    )
}



