package com.test.weatherapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.weatherapp.presentation.components.HourlyWeatherDisplay

@Composable
fun WeatherForecast(
    screenheight: Int,
    screenwidth: Int,
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.weatherDataPerDay?.get(0)?.let { data ->
        Box(modifier = Modifier
            .width((screenwidth).dp)
            .height((screenheight * 0.2).dp)
            .padding(horizontal = 16.dp)
            .background(color = Color.Black.copy(0.15f), shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = modifier.padding(16.dp)
            ) {
                Text(
                    text = "Today's Forecast",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(content = {
                    items(data) { weatherData ->
                        HourlyWeatherDisplay(
                            weatherData = weatherData,
                            modifier = Modifier
                                .height((screenheight * 0.2).dp)
                                .padding(horizontal = 16.dp),
                            screenwidth = screenwidth
                        )
                    }
                })
            }
        }
    }
}