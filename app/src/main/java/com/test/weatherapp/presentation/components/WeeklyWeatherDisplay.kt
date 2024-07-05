package com.test.weatherapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.test.weatherapp.domain.weather.WeatherWeeklyData
import java.time.format.DateTimeFormatter

@Composable
fun WeeklyWeatherDisplay(
    screenheight: Int,
    screenwidth: Int,
    weatherWeeklyData: WeatherWeeklyData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val formattedTime = remember(weatherWeeklyData) {
        weatherWeeklyData.time.format(
            DateTimeFormatter.ofPattern("dd MMMM yyyy")
        )
    }
    Column(
        modifier = modifier
            .background(
                Color.Black.copy(alpha = 0.3f),
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = formattedTime,
            style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold, color = Color.LightGray)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Image(
            painter = painterResource(id = weatherWeeklyData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.size((screenwidth*0.1).dp)
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Max Temp\n")
                }
                append("${weatherWeeklyData.apparent_temperature_max}°C")
            },
            style = MaterialTheme.typography.caption.copy(color = textColor, fontWeight = FontWeight.Light)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Min Temp\n` R")
                }
                append("${weatherWeeklyData.apparent_temperature_min}°C")
            },
            style = MaterialTheme.typography.caption.copy(color = textColor, fontWeight = FontWeight.Light)
        )

        Spacer(modifier = Modifier.height(4.dp))
    }
    Spacer(modifier = Modifier.height(8.dp))
}