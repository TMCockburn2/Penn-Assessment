package com.example.pennassessment.Data.StationAQ

data class ForecastInfo(
    val o3: List<DailyForecastParameters>,
    val pm10: List<DailyForecastParameters>,
    val pm25: List<DailyForecastParameters>,
    val uvi: List<DailyForecastParameters>
)
