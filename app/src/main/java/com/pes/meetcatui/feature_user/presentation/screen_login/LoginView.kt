package com.pes.meetcatui.feature_user.presentation.screen_login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.ui.theme.typo
import com.pes.meetcatui.R
import com.pes.meetcatui.ui.theme.Background_alt
import com.pes.meetcatui.ui.theme.Gray
import com.pes.meetcatui.ui.theme.Highlight

@Preview
@Composable
fun LoginScreen(
    //viewModel: LoginViewModel,
) {
    val viewModel = LoginViewModel()
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
                    Row (
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(horizontal = 48.dp)
                            .padding(top = 16.dp),
                    ) {
                        Subtitle(text = "Login")
                    }
                }
                item {
                    Row (modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        OutlinedTextField(
                            value = username,
                            onValueChange = { newText ->
                                username = newText
                            },
                            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                            placeholder = { Text(text = "username") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Highlight,
                                cursorColor = Highlight),
                            trailingIcon = {Icon(
                                Icons.Default.Person,
                                contentDescription = "",
                                tint = Gray,
                            )},
                            modifier = Modifier
                                .padding(horizontal = 48.dp, vertical = 8.dp)
                                .width(264.dp)
                                .background(color = Background_alt)
                        )
                        //SimpleTextField(placeholder = "username")
                    }
                }
                item {
                    Row (modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        //SimpleTextField(placeholder = "password")
                        OutlinedTextField(
                            value = password,
                            onValueChange = { newText ->
                                password = newText
                            },
                            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                            placeholder = { Text(text = "password") },
                            visualTransformation = PasswordVisualTransformation(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Highlight,
                                cursorColor = Highlight),
                            trailingIcon = {Icon(
                                Icons.Default.Lock,
                                contentDescription = "",
                                tint = Gray,
                            )},
                            singleLine = true,
                            modifier = Modifier
                                .padding(horizontal = 48.dp, vertical = 8.dp)
                                .width(264.dp)
                                .background(color = Background_alt)
                        )
                    }
                }
                item {
                    Row (
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 128.dp, vertical = 8.dp),
                    ) {
                        CustomButton(text = "Login", username = username,
                            password = password, viewModel = viewModel)
                    }
                }
                item {
                    Row (
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 48.dp)
                            .padding(top = 8.dp)) {
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
        style = typo.h2,
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
    username: String,
    password: String,
    viewModel: LoginViewModel
) {
    Button(
        onClick = {
            viewModel.Login(username = username, password = password)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Highlight,
            contentColor = Color.White
        ),
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(50)
    ) {
        Text (
            text = text,
            style = typo.body1
        )
    }
}

@Composable
fun CustomImage(
    image: Int
) {
    Image(
        painter = painterResource(id = image) ,
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
        painter = painterResource(id = image) ,
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
