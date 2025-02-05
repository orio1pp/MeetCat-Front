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
    object ReportedEvents: BottomBarScreen (
        route = "reportedEvents",
        title = "ReportedEvents",
        icon = Icons.Default.Create
    )
    object CreateEvent: BottomBarScreen (
        route = "create_events",
        title = "CreateEvent",
        icon = Icons.Default.Create
    )
    object EditEvent: BottomBarScreen (
        route = "create_events",
        title = "CreateEvent",
        icon = Icons.Default.Create
    )
    object Register: BottomBarScreen(
        route = "register",
        title = "Register",
        icon = Icons.Default.Create
    )
    object Login: BottomBarScreen(
        route = "login",
        title = "Login",
        icon = Icons.Default.Create
    )
    object UserSearch: BottomBarScreen(
        route = "user_search",
        title = "UserSearch",
        icon = Icons.Default.MailOutline
    )
    object Chats: BottomBarScreen(
        route = "chats",
        title = "Chats",
        icon = Icons.Default.MailOutline
    )
    object FriendsList: BottomBarScreen(
        route = "friends_list",
        title = "FriendsList",
        icon = Icons.Default.MailOutline
    )
    object MyEvents: BottomBarScreen(
        route = "myEvents",
        title = "MyEvents",
        icon = Icons.Default.Create
    )
    object ComingEvents: BottomBarScreen(
        route = "coming",
        title = "Coming",
        icon = Icons.Default.Create
    )
}