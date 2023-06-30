package com.test.weatherapp.presentation
import com.test.weatherapp.domain.weather.WeatherWeeklyInfo

data class WeatherWeeklyState(
    val weatherWeeklyInfo: WeatherWeeklyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
