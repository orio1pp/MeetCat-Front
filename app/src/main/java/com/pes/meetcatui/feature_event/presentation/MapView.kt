package com.pes.meetcatui.feature_event.presentation


import android.Manifest
import android.location.Location
import android.os.Looper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pes.meetcatui.R
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.pes.meetcatui.ui.theme.Background

const val MapScreenDestination = "Map"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    navToEventList: () -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
        ) {
        if (permissionState.status.isGranted) {
            displayMap(fusedLocationClient, viewModel)
        } else {
            permissionNotGranted(permissionState)
        }
    }

    navToEventList.switchViewButton(
        icon = Icons.Filled.List
    )
}

@Composable
fun displayMap(
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: MapViewModel,
) {
    val mapState by viewModel.mapState

    Text("Permission is granted")

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location : Location? ->
            if (location != null)
                viewModel.setPosition(location)
        }

    fusedLocationClient
        .requestLocationUpdates(
            viewModel.getLocationRequest(),
            viewModel.getLocationCallback(),
            Looper.getMainLooper()
        )

    if (mapState.lastLocation != null) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(mapState.gpsCoords, 10f)
            },
        ) {
            Marker(
                state = MarkerState(position = mapState.gpsCoords),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun permissionNotGranted(permissionState: PermissionState) {
    Column {
        val textToShow = if (permissionState.status.shouldShowRationale) {
            // If the user has denied the permission but the rationale can be shown,
            // then gently explain why the app requires this permission
            stringResource(R.string.msg_loc)
        } else {
            // If it's the first time the user lands on this feature, or the user
            // doesn't want to be asked again for this permission, explain that the
            // permission is required
            stringResource(R.string.loc_necessary)
        }
        Text(textToShow)
        Button(onClick = { permissionState.launchPermissionRequest() }) {
            Text(stringResource(R.string.permission))
        }
    }
}










