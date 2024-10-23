package com.example.pennassessment.Data.StationAQ


data class StationAQData(
    val idx: Int,
    val aqi: Int,
    val attributions: List<Attributions>,
    val city: CityData,
    val dominentPol: String,
    val iaqi: IaqiData,
    val time: TimeData,
    val forecast: ForecastData
)
