package com.example.pennassessment.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pennassessment.Data.Location.LocationInfo
import com.example.pennassessment.Data.Repo.StationRepositoryImpl
import com.example.pennassessment.Data.StationAQ.AcData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val stationRepo: StationRepositoryImpl,
    private val locationInfo: LocationInfo
): ViewModel() {

    /**
     * Different state variables in order to show the correct information in the UI. An improvement would be to simplify this down to one state by
     * determining what type of data is needed at run time, but for example purposes I left all 3. Here they are also all working independently
     */
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState
    private val _luiState = MutableStateFlow<LocationState>(LocationState.Idle)
    val luiState: StateFlow<LocationState> = _luiState
    private val _cState = MutableStateFlow<CityState>(CityState.Idle)
    val cState: StateFlow<CityState> = _cState

    private val mutableLiveData = MutableLiveData<AcData?>()
    val liveData: LiveData<AcData?> = mutableLiveData

    fun fetchAqByCity(cityName: String) {
        if (cityName.isEmpty()){
            _cState.value = CityState.Error("Please enter text and try again")
        }
        else {
            //sets state to loading, in case its currently idle
            _cState.value = CityState.Loading
            viewModelScope.launch {
                try {
                    val resp = stationRepo.getAQInfoByCity(cityName)?.let {
                        it.body()
                    } ?: run {
                        //successful, but null is returned
                        _cState.value =
                            CityState.Error("No city information found, please try a different city.")
                    }
                    _cState.value = CityState.Success(resp as AcData)
                } catch (e: Exception) {
                    _cState.value = CityState.Error("Error getting city information: ", e)

                }
            }
        }
    }
    fun fetchLocation() {
        _luiState.value = LocationState.Loading
        viewModelScope.launch {
            try {
                _luiState.value = LocationState.Success(getLatAndLong())
            } catch (e: Exception) {
                _luiState.value = LocationState.Error("Error receiving location: ", e)

            }
        }
    }
    fun fetchNearestStationData() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val resp = stationRepo.let {
                    it.getNearestStation(getLatAndLong().first, getLatAndLong().second)?.body()
                } ?: run {
                    _uiState.value = UiState.Error("No location information found")
                }
                _uiState.value = UiState.Success(resp as AcData)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error processing AQI data: ", e)
            }
        }
    }


    private suspend fun getLatAndLong(): Pair<Double, Double> {
        return Pair(locationInfo.currentLocation().latitude, locationInfo.currentLocation().longitude)
    }

}

sealed class UiState {
    object Idle: UiState()
    object Loading : UiState()
    data class Success(val data: AcData) : UiState()
    data class Error(val message: String, var e:Exception? = null) : UiState()
}

sealed class LocationState {
    object Idle: LocationState()
    object Loading : LocationState()
    data class Success(val latLong: Pair<Double,Double>) : LocationState()
    data class Error(val message: String, var e:Exception? = null) : LocationState()
}

sealed class CityState {
    object Idle: CityState()
    object Loading : CityState()
    data class Success(val data: AcData) : CityState()
    data class Error(val message: String, var e:Exception? = null) :CityState()
}