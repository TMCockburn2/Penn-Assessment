package com.example.pennassessment.View

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pennassessment.ViewModel.CityState
import com.example.pennassessment.ViewModel.LocationState
import com.example.pennassessment.ViewModel.StationViewModel
import com.example.pennassessment.ViewModel.UiState


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(viewModel: StationViewModel){
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState.collectAsState()
    val luiState = viewModel.luiState.collectAsState()
    val cState = viewModel.cState.collectAsState()

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(all = 10.dp)
    ){
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                //Wanted to make use of Column functions (like CicularProgressIndicator()) so instead of using lazy column,
                //I made the regular column scrollable
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ){
////////////Checking and verifying location state from view model
            when (val state = luiState.value) {
                is LocationState.Idle -> {
                    Log.i("Location State: ", "$state")
                }

                is LocationState.Loading -> {
                    CircularProgressIndicator()
                    Log.i("Location State: ", "$state")
                }

                is LocationState.Success -> {
                    Log.i("Location State Success: ", "${state.latLong}")
                    showLatLongInfo(state.latLong)
                }

                is LocationState.Error -> {
                    showError(state.message)
                    Log.e("Location Error:", state.message, state.e)
                    //error info
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Button to trigger data fetch
            Button(onClick = {
                viewModel.fetchLocation()
            }) {
                Text(text = "Fetch Location")
            }

////////////Get nearby station information
            when (val state = uiState.value) {
                is UiState.Idle -> {
                    Log.i("UI State: ", "$state")
                }

                is UiState.Loading -> {
                    CircularProgressIndicator()
                    Log.i("UI State: ", "$state")
                }

                is UiState.Success -> {
                    showNearbyStationInfo(state.data)
                    Log.i("UI State Success: ", "${state.data}")
                    state.data.let {
                        showStationForecast("O3", it.data.forecast.daily.o3)
                        showStationForecast("UVI", it.data.forecast.daily.uvi)
                        showStationForecast("PM10", it.data.forecast.daily.pm10)
                        showStationForecast("PM25", it.data.forecast.daily.pm25)
                    }
                }

                is UiState.Error -> {
                    showError(state.message)
                    Log.e("UI Error", state.message, state.e)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.fetchNearestStationData()
            }) {
                Text(text = "Fetch Nearest Station Data")
            }

////////////search for city
            when (val state = cState.value) {
                is CityState.Idle -> {
                    Log.i("City State: ", "$state")
                }

                is CityState.Loading -> {
                    CircularProgressIndicator()
                    Log.i("City State: ", "$state")
                }

                is CityState.Success -> {
                    Log.i("City State Success: ", "${state.data}")
                    state.data.let {
                        showStationForecast("O3", it.data.forecast.daily.o3)
                        showStationForecast("UVI", it.data.forecast.daily.uvi)
                        showStationForecast("PM10", it.data.forecast.daily.pm10)
                        showStationForecast("PM25", it.data.forecast.daily.pm25)
                    }
                }

                is CityState.Error -> {
                    showError(state.message)
                    Log.e("City Search Error:", state.message, state.e)
                }
            }

            // Button to trigger data fetch
            Spacer(modifier = Modifier.height(16.dp))
            var text by remember { mutableStateOf("") }
            TextField(
                value = text,
                onValueChange = { newText -> text = newText },
                label = { Text("Enter A different location") }
            )
            Button(onClick = {
                viewModel.fetchAqByCity(text)
            }) {
                Text(text = "Fetch AQI Data")
            }
        }
    }
}

@Composable
private fun showError(error:String){
    Row{
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ){
            Text(
                text = error
            )
        }
    }
}















