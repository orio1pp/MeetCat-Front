package com.pes.meetcatui.event

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pes.meetcatui.ui.theme.MeetCatUITheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.pes.meetcatui.R
import com.pes.meetcatui.common.composables.BackButton
import com.pes.meetcatui.common.composables.SpaceDp

@Composable
fun EventDate(startDate: String = "[ ]", endDate: String = "[ ]") {
    Text(
        text = "$startDate - $endDate",
        style = MaterialTheme.typography.h4,
    )
}

@Composable
fun EventDetailsItem(name: String, content: String) {
    Text(text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    SpaceDp(4)
    Text(
        text = content,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun EventDetailsLinkItem(name: String, url: String) {
    Text(text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    SpaceDp(4)
    val context = LocalContext.current
    Text(
        text = url,
        style = TextStyle(
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline,
            color = Color(color = 0xFF0645AD),
            textAlign = TextAlign.Start
        ),
        modifier = Modifier.clickable {
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))
        }
    )
}

@Composable
fun EventDetails() {
    EventDetailsItem(stringResource(R.string.location), "C. Jordi Girona, 12")
    SpaceDp(12)
    val strStartTime = "8:00"
    val strEndTime = "18:00"
    val strTimeInfo = "De $strStartTime a $strEndTime"
    EventDetailsItem(stringResource(R.string.time), strTimeInfo)
    SpaceDp(12)
    EventDetailsItem(stringResource(R.string.description), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in imperdiet dui, ut viverra ante. Morbi in eros tincidunt, convallis lorem in, commodo mauris. In ultricies turpis vel suscipit suscipit. Vestibulum a lectus nisl. Quisque viverra dolor eu nunc elementum consequat. Phasellus sed tempus velit. Vivamus et viverra enim. Proin tempus odio nec quam elementum, vel rutrum felis volutpat. Aenean dapibus consectetur luctus. Duis lacinia sem quis est venenatis, at mollis felis scelerisque.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in imperdiet dui, ut viverra ante. Morbi in eros tincidunt, convallis lorem in, commodo mauris. In ultricies turpis vel suscipit suscipit. Vestibulum a lectus nisl. Quisque viverra dolor eu nunc elementum consequat. Phasellus sed tempus velit. Vivamus et viverra enim. Proin tempus odio nec quam elementum, vel rutrum felis volutpat. Aenean dapibus consectetur luctus. Duis lacinia sem quis est venenatis, at mollis felis scelerisque.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in imperdiet dui, ut viverra ante. Morbi in eros tincidunt, convallis lorem in, commodo mauris. In ultricies turpis vel suscipit suscipit. Vestibulum a lectus nisl. Quisque viverra dolor eu nunc elementum consequat. Phasellus sed tempus velit. Vivamus et viverra enim. Proin tempus odio nec quam elementum, vel rutrum felis volutpat. Aenean dapibus consectetur luctus. Duis lacinia sem quis est venenatis, at mollis felis scelerisque.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in imperdiet dui, ut viverra ante. Morbi in eros tincidunt, convallis lorem in, commodo mauris. In ultricies turpis vel suscipit suscipit. Vestibulum a lectus nisl. Quisque viverra dolor eu nunc elementum consequat. Phasellus sed tempus velit. Vivamus et viverra enim. Proin tempus odio nec quam elementum, vel rutrum felis volutpat. Aenean dapibus consectetur luctus. Duis lacinia sem quis est venenatis, at mollis felis scelerisque.")
    SpaceDp(12)
    EventDetailsLinkItem(stringResource(R.string.link), "https://www.youtube.com/watch?v=Hvtc9BWahMQ")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeetCatUITheme {
        BackButton()
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "MeetCat's Release Party",
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = "A party",
                    style = MaterialTheme.typography.h3
                )
                EventDate(startDate = "13/10/2022")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                SpaceDp()
                EventDetails()
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SpaceDp()
                Button(onClick = {}) {
                    Text("Join placeholder")
                }
            }
        }
    }
}