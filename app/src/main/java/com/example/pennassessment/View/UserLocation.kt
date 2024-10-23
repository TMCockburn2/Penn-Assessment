package com.example.pennassessment.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun showLatLongInfo(latLong: Pair<Double, Double>) {
    Row {
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Text(
                text = "Current Latitude:",
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${latLong.first}",
                fontSize = 12.sp

            )
        }
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {

            Text(
                text = "Current Longitude:",
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${latLong.second}",
                fontSize = 12.sp

            )
        }
    }
}