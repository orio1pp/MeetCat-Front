package com.pes.meetcatui.feature_event.presentation


import android.Manifest
import android.icu.text.Transliterator.Position
import android.location.Location
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.pes.meetcatui.R
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.pes.meetcatui.ui.theme.Background
import com.pes.meetcatui.ui.theme.typo
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    navToEventList: () -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            navToEventList.switchViewButton(
                icon = Icons.Filled.List,
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()//padding(horizontal = 16.dp)
        ) {
            Column(Modifier.padding(horizontal = 16.dp)) {
                filtersSelection()
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if (permissionState.status.isGranted) {
                    displayMap(fusedLocationClient, viewModel)
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
) {
    val mapState by viewModel.mapState

    //Text("Permission is granted")
    TimedLayout()

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