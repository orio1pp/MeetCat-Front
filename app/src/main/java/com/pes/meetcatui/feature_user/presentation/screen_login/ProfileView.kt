package com.pes.meetcatui

//import com.pes.meetcatui.feature_user.presentation.screen_login.LoginView
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.pes.meetcatui.common.SpaceDp
import com.pes.meetcatui.feature_user.presentation.screen_login.LoginView
import com.pes.meetcatui.feature_user.presentation.screen_login.LoginViewModel
import com.pes.meetcatui.feature_user.presentation.screen_login.LoginViewModelFactory
import com.pes.meetcatui.feature_user.presentation.screen_login.ProfileViewModel
import com.pes.meetcatui.ui.theme.typo


@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navToUserEvents: () -> Unit, navToComingEvents: () -> Unit) {

    val notification = rememberSaveable { mutableStateOf("") }

    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    val context = LocalContext.current

    val user by viewModel.user
    val loading by viewModel.loading

    val dirty = remember { mutableStateOf(false) }
    
    val about = remember { mutableStateOf(if (user == null) "" else user!!.about) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)

        , horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.email),
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 32.dp),
                textAlign = TextAlign.Left
            )

            if (!loading) TextField(
                value = user!!.username,
                onValueChange = {

                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
            )
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = stringResource(id = R.string.bio),
                modifier = Modifier
                    .width(100.dp)
                    .padding(32.dp),
                textAlign = TextAlign.Left
            )
            if (!loading) TextField(
                value = about.value!!,
                onValueChange = { text ->
                    dirty.value = text != about.value
                    about.value = text
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
                singleLine = false,
                modifier = Modifier.height(150.dp),
                placeholder = { Text(stringResource(id = R.string.aboutPlaceholder)) }
            )
        }

        Row {
            ComingEvents(navToComingEvents)
            Spacer(modifier = Modifier.width(235.dp))
            MyEventsButton(navToUserEvents)
        }

        SpaceDp()

        Button(
            modifier = Modifier.width(120.dp),
            onClick = {
                viewModel.updateBio(about.value!!)
            },
            shape = RoundedCornerShape(32.dp),
            enabled = dirty.value,
        ) {
            Text(
                text = stringResource(id = R.string.save),
                textAlign = TextAlign.Center,
            )
        }

        SpaceDp()

        CustomButtonTancarSessio()

        SpaceDp(4)

        CustomButtonTancarCompte(viewModel)
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
    val context = LocalContext.current
    val viewModel: LoginViewModel =
        viewModel(factory = LoginViewModelFactory(context.applicationContext as Application))

    Button(
        onClick = {
            viewModel.tancarSessio()
            context.startActivity(Intent(context, LoginView::class.java))
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.hsv(0f, 0.73f, 0.69f),
            contentColor = Color.White
        ),
        modifier = Modifier.width(120.dp),
        shape = RoundedCornerShape(32.dp),
    )
    {
        Text(
            text = stringResource(id = R.string.logout),
            style = typo.body1,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CustomButtonTancarCompte(viewModel: ProfileViewModel) {
    val context = LocalContext.current
    Button(
        onClick = {
            viewModel.tancarCompte()
            context.startActivity(Intent(context, LoginView::class.java))
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.hsv(0f, 0.73f, 0.69f),
            contentColor = Color.White
        ),
        modifier = Modifier.width(120.dp),
        shape = RoundedCornerShape(32.dp),
    )
    {
        Text(
            text = stringResource(id = R.string.closeAccount),
            style = typo.body1,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun MyEventsButton(function: () -> Unit = {}) {
    FloatingActionButton(
        onClick = function,
        modifier = Modifier
            .alpha(1.0f)
            .padding(start = 16.dp, top = 16.dp)
            .clip(CircleShape)
            .border(2.dp, Color(0xFF838383), shape = CircleShape),
        backgroundColor = Color(0xFFBEBEBE),
        elevation = FloatingActionButtonDefaults.elevation(2.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(0xFF5A5A5A),
        )
    }
}

@Composable
fun ComingEvents(function: () -> Unit = {}) {
    FloatingActionButton(
        onClick = function,
        modifier = Modifier
            .alpha(1.0f)
            .padding(start = 16.dp, top = 16.dp)
            .clip(CircleShape)
            .border(2.dp, Color(0xFF838383), shape = CircleShape),
        backgroundColor = Color(0xFFBEBEBE),
        elevation = FloatingActionButtonDefaults.elevation(2.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color(0xFF5A5A5A),
        )
    }
}