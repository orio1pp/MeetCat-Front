package com.pes.meetcatui.feature_event.domain

import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.data.DataPreferences
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Bike
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Charger
import com.pes.meetcatui.network.*
import kotlinx.coroutines.Dispatchers

import com.pes.meetcatui.network.EventDetailsData
import com.pes.meetcatui.network.EventsData
import com.pes.meetcatui.network.MeetCatApi
import com.pes.meetcatui.network.green_wheel.BikeData
import com.pes.meetcatui.network.green_wheel.ChargerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class DataRepositoryImpl(
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

    override fun getEvents(pageNum: Int, title: String?): Flow<Resource<EventPage>> = flow {
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

    override fun getOwnEvents(): Flow<Resource<EventPage>> = flow {
        try {
            var accessToken: String = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getMyEvents(accessToken)
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

    override fun getAttendedEvents(): Flow<Resource<EventPage>> = flow {
        try {
            var accessToken: String = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getComingEvents(accessToken)
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

    override fun getReportedEvents(pageNum: Int, title: String?): Flow<Resource<EventPage>> = flow {
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

    override suspend fun createEvent(event: Event): String {
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

    override fun getNearestEvents(
        latitude: Double,
        longitude: Double,
        distance: Double
    ): Flow<Resource<EventPage>> = flow {
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
            val attendanceResponse =
                meetcatApi.createAttendance(AttendanceData(eventId), accessToken)
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

    override suspend fun updateEvent(event: Event): String {
        try {
            val eventSerial = buildEventDetailsData(event)
            println(eventSerial)

            var accessToken: String = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            meetcatApi.updateEvent(event.eventId, eventSerial, accessToken)
            return ("Api is successful")
        } catch (e: IOException) {
            return ("IO Exception: ${e.message}")
        } catch (e: TimeoutException) {
            return ("Timeout Exception: ${e.message}")
        } catch (e: HttpException) {
            return ("Http Exception: ${e.message}")
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

    override suspend fun likeEvent(eventId: Long) : String{
        try {
            var accessToken = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }
            meetcatApi.likeEvent(eventId, accessToken)
            return ("Api is successful")
        } catch (e: IOException) {
            return ("IO Exception: ${e.message}")
        } catch (e: TimeoutException) {
            return ("Timeout Exception: ${e.message}")
        } catch (e: HttpException) {
            return ("Http Exception: ${e.message}")
        }
    }

    override suspend fun dislikeEvent(eventId: Long) : String{
        try {
            var accessToken = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }

            meetcatApi.dislikeEvent(eventId, accessToken)
            return ("Api is successful")
        } catch (e: IOException) {
            return ("IO Exception: ${e.message}")
        } catch (e: TimeoutException) {
            return ("Timeout Exception: ${e.message}")
        } catch (e: HttpException) {
            return ("Http Exception: ${e.message}")
        }
    }

    override fun getLiked(eventId: Long): Flow<Resource<Boolean>> = flow {
        try {
            var accessToken = "Bearer "
            runBlocking(Dispatchers.IO) {
                accessToken += dataPreferences.getAccessToken().first()
            }

            emit(Resource.Loading())
            val likedResponse = meetcatApi.getLiked(eventId, accessToken)
            if (likedResponse.isSuccessful) {
                emit(Resource.Success(likedResponse.body()!!))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }
    }


    override fun getDisliked(eventId: Long): Flow<Resource<Boolean>> = flow{
        try {
            var accessToken: String = "Bearer "
             runBlocking(Dispatchers.IO) {
               accessToken += dataPreferences.getAccessToken().first()
             }
            emit(Resource.Loading())
            val dislikedResponse = meetcatApi.getDisliked(eventId, accessToken)
            if (dislikedResponse.isSuccessful) {
                emit(Resource.Success(dislikedResponse.body()!!))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }
    }

    override fun getNearestChargers(
        latitude: Double,
        longitude: Double,
        distance: Double
    ): Flow<Resource<List<Charger>>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getNearestChargers(latitude, longitude, distance)
            if (apiResponse.isSuccessful) {
                val result = apiResponse.body()!!
                val ret = mutableListOf<Charger>()
                for (chargerData: ChargerData in result) {
                    ret.add(
                        Charger(
                            id = chargerData.id,
                            latitude = chargerData.localization?.latitude,
                            longitude = chargerData.localization?.longitude,
                            chargerType = chargerData.charger_type
                        )
                    )
                }
                emit(Resource.Success(ret))
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

    override fun getNearestBikes(
        latitude: Double,
        longitude: Double,
        distance: Double
    ): Flow<Resource<List<Bike>>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = meetcatApi.getNearestBikes(latitude, longitude, distance)
            if (apiResponse.isSuccessful) {
                val result = apiResponse.body()!!
                val ret = mutableListOf<Bike>()
                for (bikeData: BikeData in result) {
                    ret.add(
                        Bike(
                            id = bikeData.id,
                            latitude = bikeData.localization?.latitude,
                            longitude = bikeData.localization?.longitude,
                            bikeTypeId = bikeData.bike_type?.id,
                            bikeTypeName = bikeData.bike_type?.name
                        )
                    )
                }
                emit(Resource.Success(ret))
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
    ): EventPage {
        val eventList = mutableListOf<Event>()
        for (event in eventsData.events) {
            eventList.add(buildEvent(event))
        }
        val eventPage = EventPage(eventList, eventsData.page!!)
        return (eventPage)
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



