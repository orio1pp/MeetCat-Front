package com.pes.meetcatui.feature_event.presentation

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.pes.meetcatui.common.Resource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Bike
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Charger
import com.pes.meetcatui.feature_event.presentation.green_wheel_api.BikeScreenState
import com.pes.meetcatui.feature_event.presentation.green_wheel_api.ChargerScreenState
import kotlinx.coroutines.launch

class MapViewModel(
    override val dataRepository: DataRepository
) : EventViewModel(dataRepository) {
    val mapState = mutableStateOf(MapScreenState())
    val chargers = mutableStateOf(ChargerScreenState())
    val bikes = mutableStateOf(BikeScreenState())
    val isAdmin = mutableStateOf(false)

    private val locationRequest = LocationRequest
        .Builder(120000)
        .build()

    init {
        viewModelScope.launch {
            initSuper()
            setData()
            setChargersData(
                mapState.value.gpsCoords.latitude,
                mapState.value.gpsCoords.longitude,
                1.0
            )
            setBikesData(
                mapState.value.gpsCoords.latitude,
                mapState.value.gpsCoords.longitude,
                1.0
            )
            checkAdmin()
        }
    }

    suspend fun checkAdmin() {
        dataRepository.getAdminStatus()
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        if (resource.data != null && resource.data == true)
                            isAdmin.value = resource.data
                    }
                    is Resource.Error -> {
                        ;
                    }
                    is Resource.Loading -> {
                        ;
                    }
                }
            }
    }

    override suspend fun setData() {
        dataRepository.getNearestEvents(
            mapState.value.gpsCoords.latitude,
            mapState.value.gpsCoords.longitude,
            1.0
        )
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        events.value = EventScreenState(
                            data = resource.data?.events as MutableList<Event>
                        )
                    }
                    is Resource.Error -> {
                        events.value = EventScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        events.value = EventScreenState(
                            isLoading = true
                        )
                    }
                }
            }
    }

    private suspend fun setChargersData(latitude: Double, longitude: Double, distance: Double) {
        dataRepository.getNearestChargers(latitude, longitude, distance)
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        chargers.value = ChargerScreenState(
                            data = resource.data as MutableList<Charger>
                        )
                    }
                    is Resource.Error -> {
                        chargers.value = ChargerScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        chargers.value = ChargerScreenState(
                            isLoading = true
                        )
                    }
                }
            }
    }

    private suspend fun setBikesData(latitude: Double, longitude: Double, distance: Double) {
        dataRepository.getNearestBikes(latitude, longitude, distance)
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        bikes.value = BikeScreenState(
                            data = resource.data as MutableList<Bike>
                        )
                    }
                    is Resource.Error -> {
                        bikes.value = BikeScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        bikes.value = BikeScreenState(
                            isLoading = true
                        )
                    }
                }
            }
    }

    fun getLocationCallback(): LocationCallback {
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

    fun refreshEventsByLocation(distance: Int) {
        viewModelScope.launch {
            dataRepository.getNearestEvents(
                mapState.value.cameraPosition.value.position.target.latitude,
                mapState.value.cameraPosition.value.position.target.longitude,
                distance.toDouble()
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        events.value = EventScreenState(
                            data = resource.data?.events as MutableList<Event>
                        )
                    }
                    is Resource.Error -> {
                        events.value = EventScreenState(
                            hasError = true,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        events.value = EventScreenState(
                            isLoading = true
                        )
                    }
                }
            }
            setBikesData(
                mapState.value.cameraPosition.value.position.target.latitude,
                mapState.value.cameraPosition.value.position.target.longitude,
                distance.toDouble()
            )
            setChargersData(
                mapState.value.cameraPosition.value.position.target.latitude,
                mapState.value.cameraPosition.value.position.target.longitude,
                distance.toDouble()
            )
        }
    }

    fun setSelectedCharger(charger: Charger) {
        chargers.value = ChargerScreenState(
            isDetailsSelected = true,
            chargerDetailsSelected = charger,
            data = chargers.value.data
        )
    }

    fun setSelectedBike(bike: Bike) {
        bikes.value = BikeScreenState(
            isDetailsSelected = true,
            bikeDetailsSelected = bike,
            data = bikes.value.data
        )
    }
}