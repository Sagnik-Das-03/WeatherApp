package com.test.weatherapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.test.weatherapp.domain.util.getGmtOffsetFromSeconds

@Composable
fun WeatherLocationCard(city: String?, state: WeatherState, screenheight: Int, screenwidth: Int) {
    val timeZoneGMT = state.weatherInfo?.offset?.let { getGmtOffsetFromSeconds(it) }
    val timeZone = state.weatherInfo?.timeZone
    val timeZoneAbbr = state.weatherInfo?.timeZoneAbbr
    val bgColor = state.weatherInfo?.currentWeatherData?.weatherType?.color
    Box(
        modifier = Modifier
            .background(color = Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height((screenheight * 0.1).dp)
            .width((screenwidth).dp),
        contentAlignment = Alignment.Center
    ){
        if (bgColor != null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .height((screenheight * 0.1).dp)
                    .width((screenwidth).dp)
            ){
                if (city != null) {
                    Text(
                        text = "${city.replace(oldValue = "_", newValue = "")} $timeZoneGMT",
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                if (timeZoneGMT != null) {
                    Text(
                        text = "$timeZone $timeZoneAbbr",
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}