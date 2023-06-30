package com.test.weatherapp.domain.weather

import java.time.LocalDate

data class WeatherWeeklyData(
    val time: LocalDate,
    val apparent_temperature_max: Double,
    val apparent_temperature_min: Double,
    val weatherType: WeatherType
)