package com.example.pennassessment.API

import com.example.pennassessment.BuildConfig
import com.example.pennassessment.Data.StationAQ.AcData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AirQualityStationAPI {

    @GET("/feed/{cityName}/?token=${BuildConfig.KEY}")
    suspend fun getStationACData(
        @Path("cityName") cityName: String
    ): Response<AcData>

    @GET("/feed/geo:{lat};{long}/?token=${BuildConfig.KEY}")
    suspend fun getNearestStation(
        @Path("lat") lat: Double,
        @Path("long") long: Double
        ): Response<AcData>

}