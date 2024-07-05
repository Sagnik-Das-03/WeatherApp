package com.test.weatherapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.weatherapp.presentation.WeatherWeeklyState

@Composable
fun WeeklyWeatherForecast(
    bgColor: Color?,
    screenheight: Int,
    screenwidth: Int,
    state: WeatherWeeklyState,
    modifier: Modifier = Modifier
) {
    if (bgColor != null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(0.dp)
                .height((screenheight * 0.7).dp)
                .width((screenwidth * 0.3).dp)){
            state.weatherWeeklyInfo?.weatherDataPerWeek?.get(0)?.let { data ->
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
                ) {
                    Column(
                        modifier = modifier
                            .background(
                                color = Color.Black.copy(0.15f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "Next 7 Day Forecast",
                            color = Color.White,
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn(content = {
                            items(data) { weatherData ->
                                WeeklyWeatherDisplay(
                                    weatherWeeklyData = weatherData,
                                    screenheight = screenheight,
                                    screenwidth = screenwidth,
                                    modifier = Modifier
                                        .height((screenheight*0.25).dp)
                                        .padding(horizontal = 8.dp)
                                )
                            }
                        })
                    }
                }
            }
        }
    }

}