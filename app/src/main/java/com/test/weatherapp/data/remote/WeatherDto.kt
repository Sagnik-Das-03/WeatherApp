package com.test.weatherapp.data.remote

import androidx.compose.ui.unit.DpOffset
import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "hourly")
    val weatherData: Hourly,
    @field:Json(name = "daily")
    val weatherWeekData: Daily,
    @field:Json(name = "timezone")
    val timeZone: String,
    @field:Json(name = "timezone_abbreviation")
    val timeZoneAbbr: String,
    @field:Json(name = "utc_offset_seconds")
    val offset: String
)
