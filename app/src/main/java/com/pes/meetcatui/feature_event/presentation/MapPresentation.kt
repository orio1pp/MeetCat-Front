package com.pes.meetcatui.feature_event.presentation


import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.*

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.pes.meetcatui.ui.theme.Background



@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun MapScreen(

) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,


        ) {
        val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionState.status.isGranted) {
            Text("Permission is granted")
            val singapore = LatLng(-1.2566527, -78.6353543)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(singapore, 10f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = singapore),
                    title = "Singapore",
                    snippet = "Marker in Singapore"
                    
                )
            }
        } else {
            Column {
                val textToShow = if (permissionState.status.shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // then gently explain why the app requires this permission
                    "The Location is important for this app. Please grant the permission."
                } else {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    "Location permission required for this feature to be available. " +
                            "Please grant the permission"
                }
                Text(textToShow)
                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text("Request permission")
                }

            }
        }


    }
}



