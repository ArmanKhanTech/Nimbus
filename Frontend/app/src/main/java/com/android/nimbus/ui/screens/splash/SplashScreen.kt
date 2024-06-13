package com.android.nimbus.ui.screen.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.nimbus.Screen
import com.android.nimbus.ui.viewmodel.SharedViewModel
import kotlinx.coroutines.delay

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val news = SharedViewModel.news.collectAsState().value
    LaunchedEffect(news) {
        delay(2000)
        if (news.articles.isNotEmpty()) {
            navController.navigate(Screen.HOME.name) {
                launchSingleTop = true
                popUpTo(Screen.HOME.name) {
                    inclusive = true
                }
            }
        }
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