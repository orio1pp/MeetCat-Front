package com.pes.meetcatui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pes.meetcatui.feature_chat.presentation.screen_friend_search.FriendSearchScreen
import com.pes.meetcatui.feature_event.domain.Event
import com.pes.meetcatui.feature_user.presentation.register_screen.RegisterViewModel
import com.pes.meetcatui.feature_user.presentation.screen_normal_login.NormalLoginScreen
import org.koin.androidx.compose.get

import com.pes.meetcatui.ui.theme.MeetCatUITheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            MeetCatUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(
                        fusedLocationClient = fusedLocationClient,
                        lastSignedAccount = GoogleSignIn.getLastSignedInAccount(this),
                        registerViewModel = getViewModel()
                    )
                }
            }
        }
    }
}

@Composable
private fun App(
    fusedLocationClient: FusedLocationProviderClient,
    lastSignedAccount: GoogleSignInAccount?,
    registerViewModel: RegisterViewModel
    ) {
    val navController = rememberNavController()

    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

    val globalEvent : MutableState<Event?> = remember { mutableStateOf(null) }

    val setVisible: (Boolean) -> Unit = { isVisible ->
        bottomBarState.value = isVisible
    }

    if (lastSignedAccount != null) {
        //Log.d("EMAIL = ", SavedPreference.EMAIL)
        val context = LocalContext.current

        SavedPreference.getUsername(context)
            ?.let {
                //Log.d("(main activity) saved preference username is", it)
                registerViewModel.tryRegister(it, it)
            }
        setVisible(true)
        AppComposable(
            navController = navController,
            initRoute = BottomBarScreen.Map.route,
            fusedLocationClient = fusedLocationClient,
            bottomBarState = bottomBarState,
            setVisible = setVisible,
            globalEvent = globalEvent,
        )
    }
    else {
        //Log.d("EMAIL = ", SavedPreference.EMAIL)
        AppComposable(
            navController = navController,
            initRoute = BottomBarScreen.Login.route,
            fusedLocationClient = fusedLocationClient,
            bottomBarState = bottomBarState,
            setVisible = setVisible,
            globalEvent = globalEvent,
        )
    }
}

@Composable
fun AppComposable(
    navController: NavHostController,
    initRoute: String,
    bottomBarState: MutableState<Boolean>,
    fusedLocationClient: FusedLocationProviderClient,
    setVisible: (Boolean) -> Unit,
    globalEvent: MutableState<Event?>,
) {
    Scaffold(
        topBar = {

        },
        bottomBar = {
            BottomBar(
                navController = navController,
                bottomBarState = bottomBarState,
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BottomNavGraph(
                navController = navController,
                initRoute = initRoute,
                fusedLocationClient = fusedLocationClient,
                setVisible = setVisible,
                globalEvent = globalEvent,
            )
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    val screens = listOf(
        BottomBarScreen.Map,
        BottomBarScreen.CreateEvent,
        BottomBarScreen.Chats,
        BottomBarScreen.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedVisibility(visible = bottomBarState.value) {
        BottomNavigation() {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}