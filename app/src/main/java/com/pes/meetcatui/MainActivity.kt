package com.pes.meetcatui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.pes.meetcatui.ui.theme.MeetCatUITheme
import org.koin.androidx.compose.getViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pes.meetcatui.feature_event.presentation.*


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
                    App(fusedLocationClient)

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
private fun App(fusedLocationClient: FusedLocationProviderClient) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController)}
    ) {
        BottomNavGraph(navController = navController)
    }

    /*NavHost(navController = navController, startDestination = BottomBarScreen.Events.route) {
        composable(BottomBarScreen.Events.route) {
            EventListScreen(getViewModel(), navtoEvent = {
                //navega cap a ell mateix, el deixo per substituir-lo pel que toqui més endavant
                navController.navigate(EventListScreenDestination)
            })
        }
        composable(BottomBarScreen.Profile.route) {
            ProfileView()
        }
    }*/
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Events,
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