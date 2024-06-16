package com.android.nimbus.ui.screen.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.nimbus.R
import com.android.nimbus.Screen
import com.android.nimbus.ui.viewmodel.SharedViewModel
import com.android.nimbus.utility.NetworkUtility
import com.android.nimbus.utility.SharedPreferenceUtility
import kotlinx.coroutines.delay

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SplashScreen(
    navController: NavController,
    isDarkMode: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sharedPreferences = SharedPreferenceUtility(context)

    var noInternet by remember { mutableStateOf(false) }

    val news = SharedViewModel.news.collectAsState().value
    LaunchedEffect(news) {
        delay(2000)
        if (NetworkUtility(context).isNetworkAvailable()) {
            if (sharedPreferences.getBooleanData("loggedIn", false)) {
                if (news.articles.isNotEmpty()) {
                    navController.navigate(Screen.HOME.name) {
                        launchSingleTop = true
                        popUpTo(Screen.SPLASH.name) {
                            inclusive = true
                        }
                    }
                }
            } else {
                navController.navigate(Screen.LOGIN.name) {
                    launchSingleTop = true
                    popUpTo(Screen.SPLASH.name) {
                        inclusive = true
                    }
                }
            }
        } else {
            noInternet = true
        }
    }

    Box(
        modifier = modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        SplashAnimation(isDarkMode, modifier)
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "App Icon",
            modifier = modifier
                .size(350.dp)
                .align(Alignment.Center)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = if (noInternet) "No Internet Connection" else "",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .padding(10.dp, 10.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun SplashAnimation(
    isDarkMode: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            if (isDarkMode.value) R.raw.splash_dark else R.raw.splash_light
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentScale = ContentScale.Crop
    )
}