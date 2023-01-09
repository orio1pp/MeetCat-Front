package com.pes.meetcatui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.location.FusedLocationProviderClient
import com.pes.meetcatui.feature_event.presentation.CreateEventView
import com.pes.meetcatui.feature_event.presentation.EventListScreen
import com.pes.meetcatui.feature_event.presentation.MapScreen
import com.pes.meetcatui.feature_user.presentation.register_screen.RegisterScreen
import com.pes.meetcatui.feature_user.presentation.screen_normal_login.NormalLoginScreen
import com.pes.meetcatui.feature_event.presentation.*
import com.pes.meetcatui.feature_event.presentation.admin_only.ReportedEventsListScreen
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    initRoute: String,
    fusedLocationClient: FusedLocationProviderClient,
    setVisible: (Boolean) -> Unit,
) {
    NavHost(navController = navController, startDestination = initRoute) {
        composable(BottomBarScreen.Events.route) {
            EventListScreen(getViewModel(), navToMap = {
                navController.navigate(BottomBarScreen.Map.route)
            })
        }
        composable(BottomBarScreen.Profile.route) {
            ProfileScreen(viewModel = getViewModel())
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
        composable(BottomBarScreen.Login.route) {
            NormalLoginScreen(
                viewModel = getViewModel(),
                navToRegister = { navController.navigate(BottomBarScreen.Register.route) },
                navToApp = { navController.navigate(BottomBarScreen.Map.route) },
                setVisible = setVisible,
            )
        }
        composable(BottomBarScreen.Register.route) {
            RegisterScreen(getViewModel())
        }
    }
}
