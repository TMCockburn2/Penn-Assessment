package com.example.pennassessment.Data.Repo

import com.example.pennassessment.API.AirQualityStationAPI
import com.example.pennassessment.Data.StationAQ.AcData
import retrofit2.Response
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(private val api: AirQualityStationAPI): StationRepository {


    override suspend fun getNearestStation(lat: Double, long: Double):Response<AcData>? {
        return try{
            api.getNearestStation(lat, long)
        }catch(e: Exception){
            null
        }
    }

    override suspend fun getAQInfoByCity(cityName: String): Response<AcData>? {
        return try{
            api.getStationACData(cityName)
        }catch(e:Exception){
            null
        }
    }
}