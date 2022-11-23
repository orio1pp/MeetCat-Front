package com.pes.meetcatui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.ui.theme.MeetCatUITheme
import org.koin.androidx.compose.getViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.pes.meetcatui.feature_event.presentation.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.pes.meetcatui.feature_user.presentation.screen_login.LoginView
import com.pes.meetcatui.feature_user.presentation.screen_login.LoginViewModel
import com.pes.meetcatui.ui.theme.typo


class ProfileView : ComponentActivity() {

        // declare the GoogleSignInClient
        lateinit var mGoogleSignInClient: GoogleSignInClient
        // val auth is initialized by lazy
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
                        ProfileScreen()
                    }
                }

            }
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("818069305025-5vhb5ef5ddlekrhrnqa866fkum6cjbdn.apps.googleusercontent.com")
                .requestEmail()
                .build()
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        }



    @Composable
    fun ProfileScreen() {

        val notification = rememberSaveable { mutableStateOf("") }

        if (notification.value.isNotEmpty()) {
            Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
            notification.value = ""
        }

        var name by rememberSaveable { mutableStateOf("default name") }
        var username by rememberSaveable { mutableStateOf("default username") }
        var bio by rememberSaveable { mutableStateOf("default bio") }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
            //.verticalScroll(rememberScrollState())
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Cancel",
                    modifier = Modifier.clickable { notification.value = "Cancelled" }
                )
                Text(text = "Save",
                    modifier = Modifier.clickable { notification.value = "Saved" }
                )
            }

            ProfileImage()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Name", modifier = Modifier.width(100.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.Black
                    )
                )
            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Username", modifier = Modifier.width(100.dp))
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.Black
                    )
                )
            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(text = "Bio", modifier = Modifier
                    .width(100.dp)
                    .padding(top = 8.dp))
                TextField(
                    value = bio,
                    onValueChange = { bio = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.Magenta
                    ),
                    singleLine = false,
                    modifier = Modifier.height(150.dp)
                )
            }

            CustomButtonTancarSessio()

        }

    }

    @Composable
    fun ProfileImage() {

        val imageUri = rememberSaveable { mutableStateOf("") }
        val painter = rememberImagePainter(
            if (imageUri.value.isEmpty())
                R.drawable.ic_profile_placeholder
            else
                imageUri.value
        )

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { imageUri.value = it.toString() }
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable { launcher.launch("image/*") },
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = "Change profile picture")
        }


    }

    @Composable
    fun CustomButtonTancarSessio() {
        val text = "Sign out"
        Button(
            onClick = {
                tancarSessio()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.hsv(0f, 0.73f, 0.69f),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50)
        )
        {
            Text(
                text = text,
                style = typo.body1
            )
        }
    }

    fun tancarSessio() {
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        val intent= Intent(this, LoginView::class.java)
        startActivity(intent)
        finish()
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MeetCatUITheme {
            ProfileScreen()
        }
    }
}