package com.pes.meetcatui.feature_event.presentation


import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.pes.meetcatui.feature_event.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.coroutines.launch


class MapViewModel(
    val dataRepository: DataRepository,
) : ViewModel() {

    val events = mutableStateOf(EventListScreenState())
    val mapState = mutableStateOf(MapScreenState())
    var selectedEvent = mutableStateOf(Event(0,"",null,null,"",null,null,null,null,null))
    val isSelected = mutableStateOf(false)
    var cameraPositionState = mutableStateOf(CameraPositionState())
    val INITIAL_LATITUDE = 41.387423
    val INITIAL_LONGITUDE = 2.169763
    var distanceRadiKm = 1.5

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

     fun refreshEventsByLocation(distance: Int) {

       viewModelScope.launch {
            dataRepository.getNearestEvents(
                cameraPositionState.value.position.target.latitude,
                cameraPositionState.value.position.target.longitude,
                distance.toDouble())
                .collect { resource ->
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

    init {
        viewModelScope.launch {
            println("Carregant events mapa")
            dataRepository.getNearestEvents(INITIAL_LATITUDE, INITIAL_LONGITUDE, distanceRadiKm)
                .collect { resource ->
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