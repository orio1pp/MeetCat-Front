package com.pes.meetcatui.feature_event.presentation

import android.location.Location
import com.google.android.gms.maps.model.LatLng

data class MapScreenState(
    val lastLocation: Location? = null,
    val gpsCoords: LatLng = LatLng(41.3870154, 2.1700471)
)
