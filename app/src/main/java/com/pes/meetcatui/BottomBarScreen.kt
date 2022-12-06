package com.pes.meetcatui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Map: BottomBarScreen (
        route = "map",
        title = "Map",
        icon = Icons.Default.LocationOn
    )
    object Profile: BottomBarScreen (
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
    object Events: BottomBarScreen (
        route = "events",
        title = "Events",
        icon = Icons.Default.Menu
    )
    object CreateEvent: BottomBarScreen (
        route = "create_events",
        title = "CreateEvent",
        icon = Icons.Default.Create
    )
    object Register: BottomBarScreen(
        route = "register",
        title = "register",
        icon = Icons.Default.Create
    )
}