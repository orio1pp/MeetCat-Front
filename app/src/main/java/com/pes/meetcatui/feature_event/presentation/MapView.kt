package com.pes.meetcatui.feature_event.presentation


import android.Manifest
import android.location.Location
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.pes.meetcatui.R
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.ui.theme.typo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    val isSelected by viewModel.isSelected

    val attendance by viewModel.attendance

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (!isSelected) {
                navToEventList.switchViewButton(
                    icon = Icons.Filled.List,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!isSelected) {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    filtersSelection()
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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
                        deselectEvent = {
                            viewModel.deselectEvent()
                        },
                        attendance = attendance,
                        onClickJoin = {
                            viewModel.addAttendance(849, it)
                        },
                        onClickUnjoin = {
                            viewModel.deleteAttendance(849, it)
                        },
                    )
                } else {
                    permissionNotGranted(permissionState)
                }
            }
        }
    }
}

@Composable
fun displayMap(
    fusedLocationClient: FusedLocationProviderClient,
    selectedEvent: Event,
    viewModel: MapViewModel,
    onEventSelectedId: (Long?) -> Unit,
    events: EventListScreenState,
    deselectEvent: () -> Unit,
    attendance: EventAttendanceState,
    onClickJoin: (Long) -> Unit,
    onClickUnjoin: (Long) -> Unit,
) {
    val mapState by viewModel.mapState

    TimedLayout()

    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(INITIAL_LATITUDE, INITIAL_LONGITUDE),
            INITIAL_ZOOM,
        )
    }

    fusedLocationClient
        .requestLocationUpdates(
            viewModel.getLocationRequest(),
            viewModel.getLocationCallback(),
            Looper.getMainLooper()
        )

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            if (location != null)
                viewModel.setPosition(location)
        }

    Column {
        Map(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            mapState,
            cameraPositionState,
            onEventClicked = {
                viewModel.isAttended(849, it.eventId)
                viewModel.onEventSelectId(it.eventId)
            },
            onOutsideClicked = {
                deselectEvent()
                onEventSelectedId(null)
            },
            events = events,
        )

        AnimatedVisibility(visible = selectedEvent.eventId != 0.toLong()) {
            if (selectedEvent != null) {
                EventDisplay(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .animateContentSize()
                        .padding(16.dp),
                    event = selectedEvent,
                    navBack = deselectEvent,
                    attendance = attendance,
                    onClickJoin = { onClickJoin(selectedEvent.eventId) },
                    onClickUnjoin = { onClickUnjoin(selectedEvent.eventId) },
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
                    if (event.location != null
                        && event.location.isNotEmpty()
                        && checkLocationFormat(event.location)
                    ) {
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
            stringResource(R.string.msg_loc)
        } else {
            stringResource(R.string.loc_necessary)
        }
        Text(textToShow)
        Button(onClick = { permissionState.launchPermissionRequest() }) {
            Text(stringResource(R.string.permission))
        }
    }
}

@Composable
private fun TimedLayout() {
    var show by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit){
        delay(5000)
        show = false
    }

    if(show){
        Popup(
            alignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp, 50.dp)
                    .padding(4.dp)
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))

            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Permission is granted",
                        textAlign = TextAlign.Center,
                        style = typo.body1,
                    )
                }
            }
        }
    }
}

@Composable
fun EventDisplay(
    modifier: Modifier,
    event: Event,
    navBack: () -> Unit,
    attendance: EventAttendanceState,
    onClickJoin: () -> Unit,
    onClickUnjoin: () -> Unit,
) {
    EventDetails(
        event = event,
        attendance = attendance,
        onClickJoin = onClickJoin,
        onClickLeave = onClickUnjoin,
    )
    BackHandlerMap() {
        navBack()
    }
    BackButton(function = navBack)
}

fun checkLocationFormat(location: String): Boolean {
    val loc = location.split(',')
    loc.forEach { coord ->
        if(coord.toDoubleOrNull() == null)
            return false
    }
    return true
}

@Composable
fun BackHandlerMap(enabled: Boolean = true, onBack: () -> Unit) {
    val currentOnBack by rememberUpdatedState(onBack)

    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}