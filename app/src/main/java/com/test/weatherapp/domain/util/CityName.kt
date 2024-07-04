package com.test.weatherapp.domain.util

import android.content.Context
import android.location.Geocoder
import java.util.Locale
import java.util.TimeZone

fun getCityName(context: Context, latitude: Double, longitude: Double): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null && addresses.isNotEmpty()) {
            addresses[0].locality  // Returns the city name
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getGmtOffsetFromSeconds(secondsString: String): String {
    val seconds = secondsString.toLongOrNull()
        ?: throw IllegalArgumentException("Invalid seconds string: $secondsString")

    val milliseconds = seconds * 1000

    val timeZone = TimeZone.getDefault()

    val offsetMillis = timeZone.getOffset(milliseconds)

    val hours = offsetMillis / 3600000
    val minutes = (offsetMillis % 3600000) / 60000

    return String.format("GMT%+d:%02d", hours, minutes)
}
