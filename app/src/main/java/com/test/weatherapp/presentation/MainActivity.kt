package com.test.weatherapp.presentation

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.test.weatherapp.domain.util.getCityName
import com.test.weatherapp.presentation.components.WeeklyWeatherForecast
import com.test.weatherapp.presentation.ui.theme.DarkBlue
import com.test.weatherapp.presentation.ui.theme.DeepBlue
import com.test.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
            viewModel.loadWeekWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        setContent {
            WeatherAppTheme {
                val backgroundColor =
                    viewModel.state.weatherInfo?.currentWeatherData?.weatherType?.color
                val screenheight = LocalConfiguration.current.screenHeightDp
                val screenwidth = LocalConfiguration.current.screenWidthDp

                val statusBarLight = Color.Transparent.toArgb()
                val statusBarDark = Color.Transparent.toArgb()
                val navigationBarLight = Color.Transparent.toArgb()
                val navigationBarDark = Color.Transparent.toArgb()
                val view = LocalView.current
                val isDarkMod = isSystemInDarkTheme()

//                DisposableEffect(isDarkMod) {
//                    val activity = view.context as Activity
//                    activity.window.statusBarColor =
//                        if(isDarkMod) ({statusBarDark})
//                        else ({statusBarLight})
//                    activity.window.navigationBarColor =
//                        if(isDarkMod) ({navigationBarDark})
//                        else ({navigationBarLight})
//
//                    WindowCompat.getInsetsController(activity.window, activity.window.decorView)
//                        ?.apply {
//                            isAppearanceLightStatusBars = !isDarkMod
//                            isAppearanceLightNavigationBars = !isDarkMod
//                        }
//
//                    onDispose { }
//                }

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val city = getCityName(
                        context = LocalContext.current,
                        latitude = viewModel.lat,
                        longitude = viewModel.lng
                    )

                    if (viewModel.state.isLoading || viewModel.weekstate.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else if (!(viewModel.state.isLoading) || !(viewModel.weekstate.isLoading)) {

                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                backgroundColor ?: Color.White,
                                                Color.DarkGray
                                            )
                                        )
                                    )
                            ) {
                                WeatherLocationCard(
                                    city = city,
                                    state = viewModel.state,
                                    screenheight = screenheight,
                                    screenwidth = screenwidth
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    WeatherCard(
                                        screenHeight = screenheight,
                                        screenWidth = screenwidth,
                                        state = viewModel.state,
                                        backgroundColor = Color.Black.copy(alpha = 0.15f)
                                    )
                                    WeeklyWeatherForecast(
                                        screenheight = screenheight,
                                        screenwidth = screenwidth,
                                        state = viewModel.weekstate,
                                        bgColor = backgroundColor
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                WeatherForecast(state = viewModel.state)
                            }
                        }
                    } else {
                        viewModel.state.error?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        viewModel.weekstate.error?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

