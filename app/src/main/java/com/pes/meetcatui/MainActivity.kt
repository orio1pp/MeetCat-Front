package com.pes.meetcatui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.pes.meetcatui.ui.theme.MeetCatUITheme
import org.koin.androidx.compose.getViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pes.meetcatui.feature_user.presentation.screen_normal_login.NormalLoginScreen


class MainActivity : ComponentActivity() {

    // declare the GoogleSignInClient
    //lateinit var mGoogleSignInClient: GoogleSignInClient

    /*private val auth by lazy {
        FirebaseAuth.getInstance()
    }*/
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

                    App(fusedLocationClient = fusedLocationClient, GoogleSignIn.getLastSignedInAccount(this))
                    /*if (GoogleSignIn.getLastSignedInAccount(this) != null) {
                        Log.d("EMAIL = ", SavedPreference.EMAIL)
                        App(fusedLocationClient)
                    }
                    else {
                        Log.d("EMAIL = ", SavedPreference.EMAIL)
                        NormalLoginScreen(viewModel = getViewModel())
                    }*/

                    /*Button(onClick = {mGoogleSignInClient.signOut().addOnCompleteListener {
                        val intent= Intent(this, LoginScreen::class.java)
                        startActivity(intent)
                        finish()
                    }}) {

                    }*/
                }
            }

        }

    }
}

@Composable
private fun App(
    fusedLocationClient: FusedLocationProviderClient,
    lastSingAccount: GoogleSignInAccount?
    ) {
    val navController = rememberNavController()
    if (lastSingAccount != null) {
        Log.d("EMAIL = ", SavedPreference.EMAIL)
        Scaffold(
            topBar = {

            },
            bottomBar = {
                BottomBar(navController = navController)
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                BottomNavGraph(navController = navController, fusedLocationClient)
            }
        }
    }
    else {
        Log.d("EMAIL = ", SavedPreference.EMAIL)
        NormalLoginScreen(
            viewModel = getViewModel(),
            navToRegister = { navController.navigate(BottomBarScreen.Register.route) }
        )
    }


}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Map,
        BottomBarScreen.CreateEvent,
        BottomBarScreen.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


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