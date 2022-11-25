package com.pes.meetcatui.feature_event.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pes.meetcatui.common.ContinuousSlider
import com.pes.meetcatui.ui.theme.typo

@Composable
fun filtersSelection() {
    Row {
        Column(modifier = Modifier.width(96.dp)) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = "Distància",
                style = typo.body1,
            )
        }
        Column {
            ContinuousSlider(10f, 50f)
        }
    }
    Row {
        Column(modifier = Modifier.width(96.dp)) {
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
}