package com.pes.meetcatui

import android.app.ListActivity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.location.FusedLocationProviderClient
import com.pes.meetcatui.feature_event.presentation.*
import org.koin.androidx.compose.getViewModel

@Composable
fun BottomNavGraph(navController: NavHostController, fusedLocationClient: FusedLocationProviderClient) {

    NavHost(navController = navController, startDestination = BottomBarScreen.Map.route) {
        composable(BottomBarScreen.Events.route) {
            EventListScreen(getViewModel(), navToMap = {
                navController.navigate(BottomBarScreen.Map.route)
            }, navToCreateEvent = {
                navController.navigate(BottomBarScreen.CreateEvent.route)
            })
        }
        composable(BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(BottomBarScreen.CreateEvent.route) {
            CreateEventView(getViewModel(), navToEvents =  {
                navController.navigate(BottomBarScreen.Events.route)
            })
        }
        composable(BottomBarScreen.Map.route) {
            MapScreen(
                viewModel = getViewModel(),
                navToEventList = { navController.navigate(BottomBarScreen.Events.route) },
                fusedLocationClient = fusedLocationClient
            )
        }
    }
}