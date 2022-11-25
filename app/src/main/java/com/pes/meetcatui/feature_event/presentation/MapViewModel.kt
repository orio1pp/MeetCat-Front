package com.pes.meetcatui.feature_event.presentation


import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MapViewModel(
    val dataRepository: DataRepository,
) : ViewModel() {

    val events = mutableStateOf(EventListScreenState())
    val mapState = mutableStateOf(MapScreenState())
    var selectedEvent = mutableStateOf(Event(0,"",null,null,"",null,null,null,null,null))
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
                    selectedEvent.value = event
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            dataRepository.getEvents(0).collect { resource ->
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
}