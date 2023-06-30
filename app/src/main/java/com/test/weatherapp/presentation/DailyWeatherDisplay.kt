package com.test.weatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.test.weatherapp.domain.weather.WeatherWeeklyData
import java.time.format.DateTimeFormatter

@Composable
fun DailyWeatherDisplay(
    weatherWeeklyData: WeatherWeeklyData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val formattedTime = remember(weatherWeeklyData) {
        weatherWeeklyData.time.format(
            DateTimeFormatter.ofPattern("dd:MMMM:yyyy")
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
            color = Color.LightGray
        )
        Image(
            painter = painterResource(id = weatherWeeklyData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "Max Temp: ${weatherWeeklyData.apparent_temperature_max}°C",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Min Temp: ${weatherWeeklyData.apparent_temperature_min}°C",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}