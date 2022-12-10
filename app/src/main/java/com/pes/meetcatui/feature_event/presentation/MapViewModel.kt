package com.pes.meetcatui.feature_event.presentation


import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.Attendance
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MapViewModel(
    val dataRepository: DataRepository,
) : ViewModel() {

    val events = mutableStateOf(EventListScreenState())
    val mapState = mutableStateOf(MapScreenState())
    var selectedEvent = mutableStateOf(Event(0,"",null,null,"",null,null,null,null,null))
    val isSelected = mutableStateOf(false)
    val attendance = mutableStateOf(EventAttendanceState())

    private val locationRequest = LocationRequest
        .Builder(120000)
        .build()

    fun getLocationCallback() : LocationCallback {
        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    mapState.value = MapScreenState(
                        lastLocation = location,
                        gpsCoords = mapState.value.gpsCoords,
                    )
                }
            }
        }
    }

    fun getLocationRequest(): LocationRequest {
        return locationRequest
    }

    fun setPosition(location: Location) {
        mapState.value = MapScreenState(
            lastLocation = mapState.value.lastLocation,
            gpsCoords = LatLng(location.latitude, location.longitude),
        )
    }

    fun onEventSelectId(eventId: Long?){
        if (eventId == null) {
            selectedEvent.value = Event(0,"",null,null,"",null,null,null,null,null)
        } else {
            events.value.data?.forEach { event ->
                if (event.eventId == eventId) {
                    isSelected.value = true
                    selectedEvent.value = event
                }
            }
        }
    }

    fun deselectEvent(){
        isSelected.value = false
        selectedEvent.value = Event(0,"",null,null,"",null,null,null,null,null)
    }

    init {
        viewModelScope.launch {
            dataRepository.getAllEvents().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        events.value = EventListScreenState(
                            data = resource.data?.events as MutableList<Event>
                        )
                    }
                    is Resource.Error -> {
                        events.value = EventListScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        events.value = EventListScreenState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun isAttended(userId: Long, eventId: Long) {
        viewModelScope.launch {
            dataRepository.getAttendance(userId, eventId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        attendance.value = EventAttendanceState(
                            isAttended = resource.data!!,
                        )
                    }
                    is Resource.Error -> {
                        attendance.value = EventAttendanceState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        attendance.value = EventAttendanceState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun addAttendance(userId: Long, eventId: Long) {
        val newAttendance = Attendance(
            userId = userId,
            eventId = eventId,
        )
        viewModelScope.launch {
            dataRepository.createAttendance(newAttendance).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        attendance.value = EventAttendanceState(
                            isAttended = true,
                        )
                    }
                    is Resource.Error -> {
                        attendance.value = EventAttendanceState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        attendance.value = EventAttendanceState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun deleteAttendance(userId: Long, eventId: Long) {
        viewModelScope.launch {
            dataRepository.deleteAttendance(userId, eventId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        attendance.value = EventAttendanceState(
                            isAttended = false,
                        )
                    }
                    is Resource.Error -> {
                        attendance.value = EventAttendanceState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        attendance.value = EventAttendanceState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}