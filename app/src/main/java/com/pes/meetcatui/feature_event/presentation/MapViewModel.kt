package com.pes.meetcatui.feature_event.presentation


import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.pes.meetcatui.feature_event.domain.DataRepository


class MapViewModel(
    val dataRepository: DataRepository,
) : ViewModel() {

    val mapState = mutableStateOf(MapScreenState())
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
}