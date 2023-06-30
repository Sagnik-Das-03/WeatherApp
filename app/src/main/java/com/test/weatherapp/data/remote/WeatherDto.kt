package com.test.weatherapp.data.remote

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "hourly")
    val weatherData: Hourly,
    @field:Json(name = "daily")
    val weatherWeekData: Daily
)
