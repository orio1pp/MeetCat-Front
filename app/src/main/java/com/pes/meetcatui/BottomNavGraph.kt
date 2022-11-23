package com.pes.meetcatui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pes.meetcatui.feature_event.presentation.EventListScreen
import com.pes.meetcatui.feature_event.presentation.EventListScreenDestination
import org.koin.androidx.compose.getViewModel


@Composable
fun BottomNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = BottomBarScreen.Events.route) {
        composable(BottomBarScreen.Events.route) {
            EventListScreen(getViewModel(), navtoEvent = {
                //navega cap a ell mateix, el deixo per substituir-lo pel que toqui més endavant
                navController.navigate(EventListScreenDestination)
            })
        }
        composable(BottomBarScreen.Profile.route) {
            ProfileView()
        }
    }
}