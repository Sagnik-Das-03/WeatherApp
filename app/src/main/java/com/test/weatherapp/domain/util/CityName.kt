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

    if (seconds == null) {
        throw IllegalArgumentException("Invalid seconds string: $secondsString")
    }

    val totalHours = seconds / 3600.0

    val hours = totalHours.toInt()
    val minutes = ((totalHours - hours) * 60).toInt()

    val sign = if (seconds >= 0) "+" else "-"

    return String.format("GMT(%s%d:%02d)", sign, Math.abs(hours), Math.abs(minutes))
}


