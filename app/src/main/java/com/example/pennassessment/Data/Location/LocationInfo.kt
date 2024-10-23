package com.example.pennassessment.Data.Location

import android.location.Location

interface LocationInfo {

    suspend fun currentLocation(): Location
}