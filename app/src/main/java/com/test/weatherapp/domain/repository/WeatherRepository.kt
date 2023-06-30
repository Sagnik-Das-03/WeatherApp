package com.test.weatherapp.domain.repository

import com.test.weatherapp.domain.util.Resource
import com.test.weatherapp.domain.weather.WeatherInfo
import com.test.weatherapp.domain.weather.WeatherWeeklyInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
    suspend fun getWeeklyWeatherData(lat: Double, long: Double) : Resource<WeatherWeeklyInfo>
}