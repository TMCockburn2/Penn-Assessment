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
import com.example.pennassessment.Data.StationAQ.AcData
import com.example.pennassessment.Data.StationAQ.Attributions
import com.example.pennassessment.Data.StationAQ.IaqiValue

@Composable
fun showNearbyStationInfo(acData: AcData){
    Row {
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Text(
                text = "Nearest Station Location",
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${acData.data.city.name}",
                fontSize = 12.sp

            )
        }
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {

            Text(
                text = "Station(s)",
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            for (station in acData.data.attributions) {
                showAllStationNames(station)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
    Row(
        modifier = Modifier.padding(all = 10.dp)
    ){
        Text(
            text = "IAQI",
            fontSize = 15.sp
        )
    }
    Row(
        modifier = Modifier.padding(all = 10.dp)
    ){
        acData.data.iaqi.let {
            NearestLocationIaqiCards("CO",it.co)
            NearestLocationIaqiCards("H",it.h)
            NearestLocationIaqiCards("NO2",it.no2)
            NearestLocationIaqiCards("O3",it.o3)
            NearestLocationIaqiCards("P",it.p)
            NearestLocationIaqiCards("PM25",it.pm25)
            NearestLocationIaqiCards("T",it.t)
            NearestLocationIaqiCards("W",it.w)
            NearestLocationIaqiCards("WG",it.wg)

        }

    }
}

@Composable
private fun NearestLocationIaqiCards(iaqiName: String, value: IaqiValue) {
    Row {
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Text(
                text = iaqiName,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${value.v}",
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun showAllStationNames(attributions: Attributions) {
    Row {
        Column {
            Text(
                text = attributions.name
            )
        }
    }
}