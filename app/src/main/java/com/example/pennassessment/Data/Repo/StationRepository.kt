package com.example.pennassessment.Data.Repo

import com.example.pennassessment.Data.StationAQ.AcData
import retrofit2.Response

interface StationRepository {

    suspend fun getNearestStation(
        lat: Double,
        long: Double): Response<AcData>?

    suspend fun getAQInfoByCity(cityName: String): Response<AcData>?

}