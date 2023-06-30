package com.test.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?daily=weathercode,apparent_temperature_max,apparent_temperature_min&timezone=auto&hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl&hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
    ): WeatherDto
}