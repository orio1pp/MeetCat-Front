package com.pes.meetcatui.feature_event.presentation


import android.Manifest
import android.location.Location
import android.os.Looper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.R
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.ui.theme.Background
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val MapScreenDestination = "Map"
private const val INITIAL_LATITUDE = 41.387423
private const val INITIAL_LONGITUDE = 2.169763
private const val INITIAL_ZOOM = 16f

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    navToEventList: () -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
) {

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val events by viewModel.events
    val selectedEvent by viewModel.selectedEvent
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        if (permissionState.status.isGranted) {
            displayMap(
                fusedLocationClient = fusedLocationClient,
                viewModel = viewModel,
                events = events,
                selectedEvent = selectedEvent,
                onEventSelectedId = {
                    viewModel.onEventSelectId(it)
                },
            )
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
    selectedEvent: Event,
    viewModel: MapViewModel,
    onEventSelectedId: (Long?) -> Unit,
    events: EventListScreenState,
) {
    val mapState by viewModel.mapState
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(INITIAL_LATITUDE, INITIAL_LONGITUDE),
            INITIAL_ZOOM,
        )
    }
    val scope = rememberCoroutineScope()
    Text("Permission is granted")

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            if (location != null)
                viewModel.setPosition(location)
        }

    fusedLocationClient
        .requestLocationUpdates(
            viewModel.getLocationRequest(),
            viewModel.getLocationCallback(),
            Looper.getMainLooper()
        )

    Column {
        Map(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            mapState,
            cameraPositionState,
            onEventClicked = {
                viewModel.onEventSelectId(it.eventId)
            },
            onOutsideClicked = {
                onEventSelectedId(null)
            },
            events = events,
        )
        AnimatedVisibility(visible = selectedEvent != null) {
            if (selectedEvent != null) {
                EventDisplay(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .animateContentSize()
                        .padding(16.dp),
                    event = selectedEvent,
                )
            }
        }
    }
}

@Composable
private fun Map(
    modifier: Modifier = Modifier,
    mapState: MapScreenState,
    cameraPositionState: CameraPositionState,
    onOutsideClicked: () -> Unit,
    events: EventListScreenState,
    onEventClicked: (Event) -> Unit
) {
    var cameraPositionState1 = cameraPositionState
    if (mapState.lastLocation != null) {
        cameraPositionState1 = rememberCameraPositionState {
            position =
                CameraPosition.fromLatLngZoom(mapState.gpsCoords, INITIAL_ZOOM)
        }
    }
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        GoogleMap(
            cameraPositionState = cameraPositionState1,
            onMapClick = { onOutsideClicked() },
        ) {
            if (events.data != null) {
                events.data.forEach { event ->
                    if (event.location != null) {
                        val ubi = event.location
                        val ll = ubi.split(',')
                        Marker(
                            state = MarkerState(
                                position = LatLng(ll[0].toDouble(), ll[1].toDouble())
                            ),
                            onClick = {
                                scope.launch {
                                    cameraPositionState1.animate(CameraUpdateFactory.newLatLng(it.position))
                                }
                                onEventClicked(event)
                                true
                            },
                        )
                    }
                }
            }
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

@Composable
fun EventDisplay(modifier: Modifier, event: Event) {
    Text(event.name)
}











