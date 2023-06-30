package com.test.weatherapp.data.remote

data class DailyUnits(
    val apparent_temperature_max: String,
    val apparent_temperature_min: String,
    val time: String,
    val weathercode: String
)