package com.pes.meetcatui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pes.meetcatui.feature_event.presentation.EventScreen
import com.pes.meetcatui.ui.theme.MeetCatUITheme
import org.koin.androidx.compose.getViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pes.meetcatui.feature_event.presentation.EventScreenDestination


class MainActivity : ComponentActivity() {

    // declare the GoogleSignInClient
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetCatUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                    Greeting("Android")
                    Button(onClick = {mGoogleSignInClient.signOut().addOnCompleteListener {
                        val intent= Intent(this, LoginScreen::class.java)
                        startActivity(intent)
                        finish()
                    }}) {

                    }
                }
            }

        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

    }
}

@Composable
private fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = EventScreenDestination) {
        composable(EventScreenDestination) {
            EventScreen(getViewModel())
        }
    }
}