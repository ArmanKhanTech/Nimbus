package com.android.nimbus

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.nimbus.ui.screen.home.HomeScreen
import com.android.nimbus.ui.screen.settings.SettingsScreen
import com.android.nimbus.ui.screen.splash.SplashScreen

enum class Screen {
    SPLASH,
    HOME,
    SETTINGS,
    LOGIN,
}

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object Settings : NavigationItem(Screen.SETTINGS.name)
    data object Login : NavigationItem(Screen.LOGIN.name)
}

@Composable
fun NimbusApp() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = NavigationItem.Splash.route
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(navController)
        }
        composable(NavigationItem.Login.route) {
            // LoginScreen(navController)
        }
    }
}