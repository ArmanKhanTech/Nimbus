package com.android.nimbus.ui.screen.splash

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.nimbus.Screen
import com.android.nimbus.viewmodel.ViewModel
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: ViewModel,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val isLoading = viewModel.isLoading.collectAsState()

    LaunchedEffect(isLoading.value) {
        if (!isLoading.value) {
            scope.launch {
                navController.navigate(Screen.HOME.name) {
                    popUpTo(Screen.SPLASH.name) {
                        inclusive = true
                    }
                    launchSingleTop = true
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