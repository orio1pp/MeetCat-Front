package com.pes.meetcatui.feature_eventList.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.ui.theme.*
import androidx.compose.runtime.*
import androidx.compose.ui.modifier.modifierLocalConsumer
import java.lang.Math.round
import kotlin.math.roundToInt


@Composable
fun EventView(name: String, desc: String, date: String, location: String) {
    Column(
        modifier = Modifier
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = Gray,
                )
            )
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 2.dp),
        ) {
            Text(
                text = name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Row() {

            Column(
                modifier = Modifier.width(192.dp)
            ) {
                Text(
                    text = desc,
                    style = typo.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Divider(
                    color = LightGray, modifier = Modifier
                        .height(48.dp)
                        .width(1.dp)
                )
            }
            Column() {
                Text(
                    text = date,
                    style = typo.body1,
                    color = LightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = location,
                    style = typo.body1,
                    color = LightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }
    }
}

@Composable
fun ContinuousSlider(start: Float, end: Float) {
    val range = start..end
    var sliderPosition by remember { mutableStateOf(start) }
    Row() {
        Text(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            text = sliderPosition.roundToInt().toString(),
            style = typo.body1,
        )
        Slider(
            value = sliderPosition,
            valueRange = range,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = Highlight,
                activeTrackColor = Highlight,
            ),
        )
    }
}

@Preview(name = "Screen")
@Composable
fun Screen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row() {
                Column (modifier = Modifier.width(96.dp)) {
                    Text(
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = "Distancia",
                        style = typo.body1,
                    )
                }
                Column {
                    ContinuousSlider(10f, 50f)
                }
            }
            Row() {
                Column (modifier = Modifier.width(96.dp)) {
                    Text(
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = "Data",
                        style = typo.body1,
                    )
                }
                Column {
                    ContinuousSlider(10f, 50f)
                }
            }
            Column(
                modifier = Modifier.height(680.dp).verticalScroll(state = ScrollState(0))
            ) {
                for (i in 1..10) {
                    EventView(
                        "FestiCAM 202$i",
                        "Festival Internacional de Teatre i Circ d’Amposta",
                        "5/10/202$i",
                        "C. de Lepant, 150"
                    )
                }
            }
            Row() {
                Text(text = "MENU SELECCION DE MENU DE MENU")
            }
        }
    }
}