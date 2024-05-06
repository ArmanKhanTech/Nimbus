package com.android.nimbus.ui.screen.splash

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.nimbus.Screen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = SplashViewModel()

    val isLoading = viewModel.isLoading.collectAsState()
    if (!isLoading.value) {
        Log.d("Splash", "SplashScreen: ${viewModel.response.value}")
        val response = viewModel.response.value
        navController.navigate("${Screen.HOME.name}/$response")
    }

    Surface(
        modifier = modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nimbus",
                modifier = modifier.padding(10.dp, 10.dp),
            )
            Text(
                text = "Splash Screen",
                modifier = modifier.padding(10.dp, 10.dp),
            )
        }
    }
}