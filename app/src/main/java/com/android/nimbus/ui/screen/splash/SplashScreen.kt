package com.android.nimbus.ui.screen.splash

import android.os.Handler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.nimbus.Screen

@Composable
fun SplashScreen(
    navController: NavController
) {
    val context = LocalContext.current

    Handler(context.mainLooper).postDelayed({
        navController.navigate(Screen.HOME.name)
    }, 3000)

    Surface(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nimbus",
                modifier = Modifier.padding(10.dp, 10.dp),
            )
            Text(
                text = "Splash Screen",
                modifier = Modifier.padding(10.dp, 10.dp),
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        navController = rememberNavController()
    )
}