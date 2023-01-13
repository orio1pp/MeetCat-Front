package com.pes.meetcatui.feature_event.presentation

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

data class MapScreenState(
    val lastLocation: Location? = null,
    val gpsCoords: LatLng = LatLng(41.387423, 2.169763),
    val cameraPosition: MutableState<CameraPositionState> = mutableStateOf(CameraPositionState()),
)