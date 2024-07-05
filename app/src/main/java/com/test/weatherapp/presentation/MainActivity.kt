package com.test.weatherapp.presentation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.test.weatherapp.domain.util.getCityName
import com.test.weatherapp.presentation.components.WeeklyWeatherForecast
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
                val view = LocalView.current
                val isDarkMod = isSystemInDarkTheme()

                HideSystemBars(context = LocalContext.current)
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
                                WeatherForecast(state = viewModel.state, screenheight = screenheight, screenwidth = screenwidth)
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

@Composable
fun HideSystemBars(context: Context) {

    DisposableEffect(Unit) {
        val window = context.findActivity()?.window ?: return@DisposableEffect onDispose {}
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)

        insetsController?.apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        onDispose {
            insetsController?.apply {
                show(WindowInsetsCompat.Type.statusBars())
                show(WindowInsetsCompat.Type.navigationBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
            }
        }
    }
}
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}