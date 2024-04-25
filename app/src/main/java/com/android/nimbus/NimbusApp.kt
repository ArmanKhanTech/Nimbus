package com.android.nimbus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.nimbus.ui.screen.feed.FeedScreen
import com.android.nimbus.ui.screen.home.HomeScreen
import com.android.nimbus.ui.screen.settings.SettingsScreen
import com.android.nimbus.ui.screen.splash.SplashScreen

enum class Screen {
    SPLASH,
    HOME,
    FEED,
    TOPICS,
    WEB,
    SETTINGS,
    RELEVANCE,
    LOGIN,
    SIGNUP
}

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object Feed : NavigationItem(Screen.FEED.name)
    data object Topics : NavigationItem(Screen.TOPICS.name)
    data object Web : NavigationItem(Screen.WEB.name)
    data object Settings : NavigationItem(Screen.SETTINGS.name)
    data object Relevance : NavigationItem(Screen.RELEVANCE.name)
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
        composable(NavigationItem.Topics.route) {
            // TopicsScreen(navController)
        }
        composable(NavigationItem.Web.route) {
            // WebScreen(navController)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(navController, isDarkMode, modifier)
        }
        composable(NavigationItem.Relevance.route) {
            // RelevanceScreen(navController)
        }
        composable(NavigationItem.Login.route) {
            // LoginScreen(navController)
        }
        composable(NavigationItem.Signup.route) {
            // SignupScreen(navController)
        }
    }
}