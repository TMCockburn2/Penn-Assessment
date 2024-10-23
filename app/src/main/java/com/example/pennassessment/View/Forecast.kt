package com.example.pennassessment.View

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pennassessment.Data.StationAQ.DailyForecastParameters
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun showStationForecast(forecastName: String, list: List<DailyForecastParameters>) {
    Column {
        Text(
            text = forecastName,
            fontSize = 15.sp
        )
    }
    Row(
        modifier = Modifier.padding(all = 10.dp)
    ) {
        buildForecastCards(filterDays(list))
    }
}

@Composable
private fun buildForecastCards(list: List<DailyForecastParameters>) {
    for (date in list) {
        ForecastCard(date)
    }
}

@Composable
fun ForecastCard(item: DailyForecastParameters) {
    Column(
        modifier = Modifier.padding(all = 5.dp)
    ) {
        Text(
            text = item.day
        )
        Row {
            //low
            Column(
                modifier = Modifier.padding(all = 2.dp)
            ) {
                Text(
                    text = "Low"
                )
                Text(
                    text = "${item.min}"
                )
            }
            //avg
            Column(
                modifier = Modifier.padding(all = 2.dp)
            ) {
                Text(
                    text = "Avg"
                )
                Text(
                    text = "${item.avg}"
                )
            }
            //high
            Column(
                modifier = Modifier.padding(all = 2.dp)
            ) {
                Text(
                    text = "High"
                )
                Text(
                    text = "${item.max}"
                )
            }
        }
    }
}

/**
 * Converts the day string to localData class, and compares the date values to determine what is yesterday and what is tomorrow. For some reason the normal
 * means of doing this (comparing plus 1 day minus 1 day) wasn't working, so I had to get a bit creative to find a feasible solution. In this case it's essentially
 * making 2 bounds so no dates outside of the 3 day range are selected
 */
@RequiresApi(Build.VERSION_CODES.O)
private fun filterDays(dateList: List<DailyForecastParameters>): List<DailyForecastParameters>{
    return dateList.filter {
        LocalDate.parse(it.day).equals(LocalDate.now()) ||
                LocalDate.parse(it.day).isBefore(LocalDate.now().plusDays(2)) &&
                LocalDate.parse(it.day).isAfter(LocalDate.now().minusDays(2))

    }
}