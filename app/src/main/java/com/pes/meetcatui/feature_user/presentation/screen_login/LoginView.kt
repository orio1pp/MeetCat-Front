package com.pes.meetcatui.feature_user.presentation.screen_login

import android.R.color
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.pes.meetcatui.*
import com.pes.meetcatui.R
import com.pes.meetcatui.feature_user.presentation.register_screen.RegisterViewModel
import com.pes.meetcatui.ui.theme.*
import org.koin.androidx.compose.getViewModel


class LoginView : ComponentActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    val firebaseAuth = FirebaseAuth.getInstance()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetCatUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen()
                }
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("818069305025-5vhb5ef5ddlekrhrnqa866fkum6cjbdn.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }


    @Preview
    @Composable
    fun LoginScreen(
        //viewModel: LoginViewModel
    ) {
        val regviewModel: RegisterViewModel
        regviewModel = getViewModel()

        val viewModel: LoginViewModel =

            androidx.lifecycle.viewmodel.compose.viewModel(factory = LoginViewModelFactory(this.applicationContext as Application))
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                CustomImage(image = R.drawable.yellow_wave)
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight()
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 48.dp)
                                .padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Title(text = "MeetCAT")
                            CustomLogo(image = R.drawable.icon)
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 48.dp)
                                .padding(top = 16.dp),
                        ) {
                            Subtitle(text = "Sign in with Google: ")
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 128.dp, vertical = 8.dp),

                            ) {
                            CustomButtonGoogle(
                                text = "Login", viewModel = viewModel, regviewModel = regviewModel
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 48.dp)
                                .padding(top = 16.dp),
                        ) {
                            Subtitle(text = "Or Sign in with your email/username: ")
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 128.dp, vertical = 8.dp),

                            ) {
                            CustomButton(
                                text = "Login"
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 48.dp)
                                .padding(top = 8.dp)
                        ) {
                            WarningText(text = viewModel.warning.value)
                        }
                    }
                }
            }
        }

    }


    @Composable
    fun Title(
        text: String
    ) {
        Text(
            text = text,
            style = typo.h1,
        )
    }

    @Composable
    fun Subtitle(
        text: String
    ) {
        Text(
            text = text,
            style = typo.h3,
        )
    }

    @Composable
    fun WarningText(
        text: String
    ) {
        Text(
            text = text,
            style = typo.body1,
            color = Color.Red,
        )
    }

    @Composable
    fun CustomButton(
        text: String,
    ) {
        Button(
            onClick = {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Highlight,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(50)
        )
        {
            Text(
                text = text,
                style = typo.body1
            )
        }
    }


    @Composable
    fun CustomButtonGoogle(
        text: String,
        viewModel: LoginViewModel,
        regviewModel: RegisterViewModel
    ) {
        var username by remember { mutableStateOf(SavedPreference.USERNAME) }
        var password by remember { mutableStateOf(SavedPreference.EMAIL) }
            Button(
                onClick = {
                    signInGoogle(viewModel)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                contentPadding = PaddingValues(15.dp),
                border = BorderStroke(1.dp, Color.Gray)
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterVertically)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = "",
                        alignment = Alignment.TopStart,
                        modifier = Modifier
                            .scale(scaleX = 0.5f, scaleY = 0.5f)
                    )
                }
            }

    }


    @Composable
    fun CustomImage(
        image: Int
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            alignment = Alignment.TopStart,
            modifier = Modifier
                .scale(scaleX = 3.25f, scaleY = 3f)
                .rotate(180f)
        )
    }

    @Composable
    fun CustomLogo(
        image: Int
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(90.dp)
                .padding(bottom = 32.dp)

        )
    }

    @Composable
    fun SimpleTextField(
        placeholder: String,
    ) {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            placeholder = { Text(text = placeholder) },
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 8.dp)
                .width(264.dp)
                .background(color = Background_alt)
        )
    }

    private fun signInGoogle(viewModel: LoginViewModel) {

        val signInIntent = viewModel.getGoogleClient().signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    // onActivityResult() function : this is where we provide the task and data for the Google Account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    // handleResult() function -  this is where we update the UI after Google signin takes place
    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // UpdateUI() function - this is where we specify what UI updation are needed after google signin has taken place.
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val viewModel: RegisterViewModel
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                SavedPreference.setEmail(this, account.email.toString())
                SavedPreference.setUsername(this, account.displayName.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
