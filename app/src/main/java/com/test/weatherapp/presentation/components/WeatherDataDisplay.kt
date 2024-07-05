package com.test.weatherapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun WeatherDataDisplay(
    screenheight: Int,
    screenWidth: Int,
    value: Int,
    type: String,
    unit: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    iconTint: Color = Color.White
) {
    Box(modifier = Modifier
        .padding(2.dp)
        .size((screenWidth*0.2).dp)
        .background(color = Color.White.copy(0.15f), shape = RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center){
        Column(
            modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size((screenWidth*0.06).dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = type,
                style = textStyle
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$value $unit",
                style = textStyle.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}