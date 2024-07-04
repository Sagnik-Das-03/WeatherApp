package com.test.weatherapp.data.mappers

import com.test.weatherapp.data.remote.Daily
import com.test.weatherapp.data.remote.Hourly
import com.test.weatherapp.data.remote.WeatherDto
import com.test.weatherapp.domain.weather.WeatherData
import com.test.weatherapp.domain.weather.WeatherInfo
import com.test.weatherapp.domain.weather.WeatherType
import com.test.weatherapp.domain.weather.WeatherWeeklyData
import com.test.weatherapp.domain.weather.WeatherWeeklyInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)
 private data class IndexedWeeklyWeatherData(
     val index: Int,
     val data: WeatherWeeklyData
 )
fun Hourly.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperature_2m[index]
        val weatherCode = weathercode[index]
        val windSpeed = windspeed_10m[index]
        val pressure = pressure_msl[index]
        val humidity = relativehumidity_2m[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val timeZone = timeZone
    val timezoneAbbr =timeZoneAbbr
    val offset = offset
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData,
        timeZone = timeZone,
        timeZoneAbbr = timezoneAbbr,
        offset = offset
    )
}

fun Daily.toWeeklyWeatherDataMap():  Map<Int, List<WeatherWeeklyData>> {
    return time.mapIndexed { index, time ->
        val maxTemperature = apparent_temperature_max[index]
        val minTemperature = apparent_temperature_min[index]
        val weatherCode = weathercode[index]
        IndexedWeeklyWeatherData(
            index = index,
            data = WeatherWeeklyData(
                time = LocalDate.parse(time, DateTimeFormatter.ISO_DATE),
                apparent_temperature_max = maxTemperature,
                apparent_temperature_min = minTemperature,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index/7
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherWeekInfo(): WeatherWeeklyInfo {
    val weatherDataWeelyMap = weatherWeekData.toWeeklyWeatherDataMap()
    val now = LocalDate.now()
    val currentWeatherData = weatherDataWeelyMap[0]?.find {
        val day = if(now.dayOfWeek.value < 3) now.dayOfWeek.value else now.dayOfWeek.value + 1
        it.time.dayOfWeek.value == day
    }
    return WeatherWeeklyInfo(
        weatherDataPerWeek = weatherDataWeelyMap,
        currentDayWeatherData = currentWeatherData
    )
}
