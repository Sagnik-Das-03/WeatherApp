package com.test.weatherapp.domain.weather

data class WeatherWeeklyInfo(
    val weatherDataPerWeek: Map<Int, List<WeatherWeeklyData>>,
    val currentDayWeatherData: WeatherWeeklyData?
)
