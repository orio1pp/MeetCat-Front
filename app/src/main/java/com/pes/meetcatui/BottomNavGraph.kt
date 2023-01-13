package com.pes.meetcatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.location.FusedLocationProviderClient
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_event.presentation.CreateOrEditEventView
import com.pes.meetcatui.feature_event.presentation.EventListScreen
import com.pes.meetcatui.feature_event.presentation.MapScreen
import com.pes.meetcatui.feature_event.presentation.user_events.EventListScreenAttending
import com.pes.meetcatui.feature_event.presentation.user_events.EventListScreenOwn
import com.pes.meetcatui.feature_user.presentation.register_screen.RegisterScreen
import com.pes.meetcatui.feature_user.presentation.screen_normal_login.NormalLoginScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    initRoute: String,
    fusedLocationClient: FusedLocationProviderClient,
    setVisible: (Boolean) -> Unit,
    globalEvent: MutableState<Event?>
) {
    NavHost(navController = navController, startDestination = initRoute) {
        composable(BottomBarScreen.Events.route) {
            EventListScreen(
                viewModel = getViewModel(),
                globalEvent = globalEvent,
                navToEditEvent = { navController.navigate(BottomBarScreen.EditEvent.route) },
                navToMap = {
                    navController.navigate(BottomBarScreen.Map.route)
                }
            )
        }
        composable(BottomBarScreen.Profile.route) {
            ProfileScreen(
                viewModel = getViewModel(),
                navToUserEvents = {
                    navController.navigate(BottomBarScreen.MyEvents.route)
                },
                navToComingEvents = {
                    navController.navigate(BottomBarScreen.ComingEvents.route)
                }
            )
        }
        composable(BottomBarScreen.CreateEvent.route) {
            CreateOrEditEventView(
                viewModel = getViewModel(),
                event = null,
                navToEvents = {
                    navController.navigate(BottomBarScreen.Events.route)
                }
            )
        }
        composable(BottomBarScreen.MyEvents.route) {
            EventListScreenOwn(
                viewModel = getViewModel(),
                globalEvent = globalEvent,
                navToEditEvent = { navController.navigate(BottomBarScreen.EditEvent.route) },
                navToMap = {
                    navController.navigate(BottomBarScreen.Map.route)
                }
            )
        }
        composable(BottomBarScreen.ComingEvents.route) {
            EventListScreenAttending(
                viewModel = getViewModel(),
                globalEvent = globalEvent,
                navToEditEvent = { navController.navigate(BottomBarScreen.EditEvent.route) },
                navToMap = {
                    navController.navigate(BottomBarScreen.Map.route)
                }
            )
        }
        composable(BottomBarScreen.EditEvent.route) {
            CreateOrEditEventView(
                viewModel = getViewModel(),
                event = globalEvent.value,
                navToEvents = {
                    navController.navigate(BottomBarScreen.Events.route)
                }
            )
        }
        composable(BottomBarScreen.Map.route) {
            MapScreen(
                viewModel = getViewModel(),
                globalEvent = globalEvent,
                navToEditEvent = { navController.navigate(BottomBarScreen.EditEvent.route) },
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
