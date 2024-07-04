package com.test.weatherapp.domain.weather

import androidx.compose.ui.unit.DpOffset
import java.util.TimeZone

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    val timeZone: String?,
    val timeZoneAbbr: String?,
    val offset: String?
)
