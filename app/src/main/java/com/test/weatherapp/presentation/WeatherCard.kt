package com.test.weatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import com.test.weatherapp.R
import com.test.weatherapp.domain.util.getGmtOffsetFromSeconds
import com.test.weatherapp.presentation.components.WeatherDataDisplay

@Composable
fun WeatherCard(
    screenHeight: Int,
    screenWidth: Int,
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.currentWeatherData?.let { data ->
        Box(
            modifier = modifier
                .height((screenHeight * 0.7).dp)
                .width((screenWidth * 0.75).dp)
                .padding(16.dp)
                .background(color = backgroundColor, shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Today",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.Start),
                    color = Color.White
                )
                Text(
                    text = data.time.format(
                        DateTimeFormatter.ofPattern("MMMM dd, EEEE")
                    ),
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Start),
                    color = Color.White
                )
                Text(
                    text = "${data.time.format(DateTimeFormatter.ofPattern("HH:mm"))} " +
                            "${state.weatherInfo.timeZoneAbbr}",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.Start),
                    color = Color.White)
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.width((screenWidth*0.4).dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperatureCelsius}°C",
                    style = MaterialTheme.typography.h2.copy(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.ExtraBold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.weatherType.weatherDesc,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        type = "Pressure",
                        value = data.pressure.roundToInt(),
                        unit = "hpa",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                        iconTint = Color.White,
                        textStyle = MaterialTheme.typography.subtitle1,
                        screenheight = screenHeight,
                        screenWidth = screenWidth
                    )
                    WeatherDataDisplay(
                        type = "Humidity",
                        value = data.humidity,
                        unit = "%",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                        iconTint = Color.White,
                        textStyle = MaterialTheme.typography.subtitle1,
                        screenheight = screenHeight,
                        screenWidth = screenWidth
                    )
                    WeatherDataDisplay(
                        type = "Wind",
                        value = data.windSpeed.roundToInt(),
                        unit = "km/h",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                        iconTint = Color.White,
                        textStyle = MaterialTheme.typography.subtitle1,
                        screenWidth = screenWidth,
                        screenheight = screenHeight
                    )
                }
            }
        }
    }
}