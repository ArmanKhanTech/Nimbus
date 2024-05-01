package com.android.nimbus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.nimbus.ui.screen.auth.screen.LoginScreen
import com.android.nimbus.ui.screen.auth.screen.SignupScreen
import com.android.nimbus.ui.screen.feeds.FeedScreen
import com.android.nimbus.ui.screen.home.HomeScreen
import com.android.nimbus.ui.screen.settings.SettingsScreen
import com.android.nimbus.ui.screen.splash.SplashScreen
import com.android.nimbus.ui.screen.web.WebScreen

enum class Screen {
    SPLASH,
    HOME,
    FEED,
    WEB,
    SETTINGS,
    LOGIN,
    SIGNUP
}

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object Feed : NavigationItem(Screen.FEED.name)
    data object Web : NavigationItem(Screen.WEB.name)
    data object Settings : NavigationItem(Screen.SETTINGS.name)
    data object Login : NavigationItem(Screen.LOGIN.name)
    data object Signup : NavigationItem(Screen.SIGNUP.name)
}

@Composable
fun NimbusApp(
    isDarkMode: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = NavigationItem.Splash.route
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen(navController, modifier)
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(navController, isDarkMode, modifier)
        }
        composable(NavigationItem.Feed.route) {
            FeedScreen(navController, isDarkMode, "Feed", modifier)
        }
        composable(NavigationItem.Web.route) {
            WebScreen(navController)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(navController, isDarkMode, modifier)
        }
        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }
        composable(NavigationItem.Signup.route) {
            SignupScreen(navController)
        }
    }
}