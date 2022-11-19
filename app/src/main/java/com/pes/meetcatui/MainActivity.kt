package com.pes.meetcatui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pes.meetcatui.ui.theme.MeetCatUITheme
import org.koin.androidx.compose.getViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.pes.meetcatui.feature_event.presentation.*


class MainActivity : ComponentActivity() {

    // declare the GoogleSignInClient
    //lateinit var mGoogleSignInClient: GoogleSignInClient

    /*private val auth by lazy {
        FirebaseAuth.getInstance()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetCatUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()

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
private fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CreateEventDestination) {
        composable(CreateEventDestination) {
            CreateEventView()
        }
        /*composable(EventScreenDestination) {
            EventScreen(viewmodel, navBack = {
                navController.navigate(EventListScreenDestination)
            })
        }*/
    }
}