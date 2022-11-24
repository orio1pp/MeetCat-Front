package com.pes.meetcatui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pes.meetcatui.ui.theme.MeetCatUITheme
import org.koin.androidx.compose.getViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pes.meetcatui.feature_event.presentation.*
import org.koin.androidx.compose.viewModel


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

    NavHost(navController = navController, startDestination = CreateEventDestination) {
        composable(CreateEventDestination) {
            CreateEventView(getViewModel(), navToEvents =  {
                navController.navigate(EventListScreenDestination)
            })
        }
        /*composable(EventScreenDestination) {
            EventScreen(viewmodel, navBack = {
                navController.navigate(EventListScreenDestination)
            })
        }*/
    }
}
