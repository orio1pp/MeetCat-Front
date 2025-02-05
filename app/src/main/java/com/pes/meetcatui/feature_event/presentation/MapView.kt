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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.pes.meetcatui.R
import com.pes.meetcatui.common.BackButton
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.green_wheel_api.BikeScreenState
import com.pes.meetcatui.feature_event.presentation.green_wheel_api.ChargerScreenState
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
    globalEvent: MutableState<Event?>,
    navToEventList: () -> Unit,
    navToEditEvent: () -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
    navToReportedEvents: () -> Unit
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    val events by viewModel.events
    val chargers by viewModel.chargers
    val bikes by viewModel.bikes
    val mapState by viewModel.mapState
    val attendance by viewModel.attendance
    val isAdmin by viewModel.isAdmin

    val distanceFilter = remember { mutableStateOf(1) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Column() {
                if (!events.isDetailsSelected) {
                    FloatingActionButton(
                        onClick = { viewModel.refreshEventsByLocation(distanceFilter.value) },
                        modifier = Modifier
                            .alpha(1.0f)
                            .padding(top = 16.dp, end = 304.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF838383), shape = CircleShape),
                        backgroundColor = Color(0xFFBEBEBE),
                        elevation = FloatingActionButtonDefaults.elevation(2.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF5A5A5A),
                        )
                    }
                    navToEventList.switchViewButton(
                        icon = Icons.Filled.List,
                    )
                    Row() {
                        AnimatedVisibility(visible = isAdmin) {
                            navToReportedEvents.switchViewButton(icon = Icons.Filled.List)
                        }
                    }
                }
            }

        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!events.isDetailsSelected) {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    filtersSelection(distanceFilter = distanceFilter)
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
                        chargers = chargers,
                        bikes = bikes,
                        onEventSelected = {
                            viewModel.setSelectedEvent(it!!)
                        },
                        deselectEvent = {
                            viewModel.setNotSelected()
                        },
                        attendance = attendance,
                        onClickJoin = {
                            viewModel.addAttendance(it)
                        },
                        onClickUnjoin = {
                            viewModel.deleteAttendance(it)
                        },
                        navToEdit = navToEditEvent,
                        globalEvent = globalEvent,
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
    viewModel: MapViewModel,
    onEventSelected: (Event?) -> Unit,
    events: EventScreenState,
    chargers: ChargerScreenState,
    bikes: BikeScreenState,
    deselectEvent: () -> Unit,
    attendance: EventAttendanceState,
    onClickJoin: (Long) -> Unit,
    onClickUnjoin: (Long) -> Unit,
    globalEvent: MutableState<Event?>,
    navToEdit: () -> Unit,
) {
    val mapState by viewModel.mapState

    TimedLayout()

    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(mapState.gpsCoords.latitude, mapState.gpsCoords.longitude),
            INITIAL_ZOOM,
        )
    }

    fusedLocationClient.requestLocationUpdates(
        viewModel.getLocationRequest(), viewModel.getLocationCallback(), Looper.getMainLooper()
    )

    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) viewModel.setPosition(location)
    }

    Column {
        Map(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            mapState,
            cameraPositionState,
            onEventClicked = onEventSelected,
            onOutsideClicked = {
                deselectEvent()
            },
            events = events,
            chargers = chargers,
            bikes = bikes,
            viewModel = viewModel
        )

        AnimatedVisibility(visible = events.isDetailsSelected) {
            if (events.eventDetailsSelected != null) {
                EventDisplay(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .animateContentSize()
                        .padding(16.dp),
                    event = events.eventDetailsSelected,
                    navBack = deselectEvent,
                    attendance = attendance,
                    getIsUsers = { viewModel.getIsUsers(events.eventDetailsSelected) },
                    onClickJoin = { onClickJoin(events.eventDetailsSelected.eventId) },
                    onClickUnjoin = { onClickUnjoin(events.eventDetailsSelected.eventId) },
                    deleteEvent = { viewModel.deleteEvent(events.eventDetailsSelected.eventId) },
                    globalEvent = globalEvent,
                    navToEdit = navToEdit,
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
    events: EventScreenState,
    chargers: ChargerScreenState,
    bikes: BikeScreenState,
    onEventClicked: (Event) -> Unit,
    viewModel: MapViewModel
) {
    var cameraPositionState1 = cameraPositionState
    if (mapState.lastLocation != null) {
        cameraPositionState1 = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(mapState.gpsCoords, INITIAL_ZOOM)
        }
    }

    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        viewModel.mapState.value.cameraPosition.value = cameraPositionState1

        GoogleMap(
            cameraPositionState = mapState.cameraPosition.value,
            onMapClick = { onOutsideClicked() },
        ) {
            if (events.data != null) {
                events.data.forEach { event ->
                    if (event.location != null && event.location.isNotEmpty() && checkLocationFormat(
                            event.location
                        )
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
            if (chargers.data != null) {
                chargers.data.forEach { charger ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                charger.latitude!!.toDouble(),
                                charger.longitude!!.toDouble()
                            )
                        ),
                        onClick = {
                            scope.launch {
                                cameraPositionState1.animate(CameraUpdateFactory.newLatLng(it.position))
                            }
                            viewModel.setSelectedCharger(charger)
                            true
                        },
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    )
                }
            }
            if (bikes.data != null) {
                bikes.data.forEach { bike ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                bike.latitude!!.toDouble(),
                                bike.longitude!!.toDouble()
                            )
                        ),
                        onClick = {
                            scope.launch {
                                cameraPositionState1.animate(CameraUpdateFactory.newLatLng(it.position))
                            }
                            viewModel.setSelectedBike(bike)
                            true
                        },
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                    )
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

    LaunchedEffect(key1 = Unit) {
        delay(5000)
        show = false
    }

    if (show) {
        Popup(
            alignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp, 50.dp)
                    .padding(4.dp)
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))

            ) {
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
    getIsUsers: () -> Boolean,
    onClickJoin: () -> Unit,
    onClickUnjoin: () -> Unit,
    deleteEvent: () -> Unit,
    globalEvent: MutableState<Event?>,
    navToEdit: () -> Unit,
) {
    EventDetails(
        event = event,
        globalEvent = globalEvent,
        attendance = attendance,
        getIsUsers = getIsUsers,
        onClickJoin = onClickJoin,
        onClickLeave = onClickUnjoin,
        deleteEvent = deleteEvent,
        navToEdit = navToEdit,
        admin = false,
    )
    BackHandlerMap() {
        navBack()
    }
    BackButton(function = navBack)
}

fun checkLocationFormat(location: String): Boolean {
    val loc = location.split(',')
    loc.forEach { coord ->
        if (coord.toDoubleOrNull() == null) return false
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