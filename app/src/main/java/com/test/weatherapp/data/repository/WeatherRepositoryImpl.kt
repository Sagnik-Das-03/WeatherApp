package com.test.weatherapp.data.repository

import com.test.weatherapp.data.mappers.toWeatherInfo
import com.test.weatherapp.data.mappers.toWeatherWeekInfo
import com.test.weatherapp.data.remote.WeatherApi
import com.test.weatherapp.domain.repository.WeatherRepository
import com.test.weatherapp.domain.util.Resource
import com.test.weatherapp.domain.weather.WeatherInfo
import com.test.weatherapp.domain.weather.WeatherWeeklyInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long,

                ).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getWeeklyWeatherData(
        lat: Double,
        long: Double
    ): Resource<WeatherWeeklyInfo> {
        return try{
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherWeekInfo()
            )
        } catch (e:Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occured")
        }

    }
}