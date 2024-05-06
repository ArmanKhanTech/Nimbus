package com.android.nimbus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.nimbus.model.NewsModel
import com.android.nimbus.ui.screen.auth.screen.LoginScreen
import com.android.nimbus.ui.screen.auth.screen.SignupScreen
import com.android.nimbus.ui.screen.feeds.FeedScreen
import com.android.nimbus.ui.screen.home.HomeScreen
import com.android.nimbus.ui.screen.settings.SettingsScreen
import com.android.nimbus.ui.screen.splash.SplashScreen

enum class Screen {
    SPLASH,
    HOME,
    FEEDS,
    SETTINGS,
    LOGIN,
    SIGNUP
}

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object Feeds : NavigationItem(Screen.FEEDS.name)
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
        composable(
            "${NavigationItem.Home.route}/{response}",
            arguments = listOf(
                navArgument("response") {
                    type = NavType.StringType
                }
            )
        ) {
            val response = it.arguments?.getString("response")
            if (response != null) {
                HomeScreen(navController, response, isDarkMode, modifier)
            }
        }
        composable(NavigationItem.Feeds.route) {
            val newsModel: NewsModel? = navController.previousBackStackEntry?.savedStateHandle?.get("newsModel")
            val category: String? = navController.previousBackStackEntry?.savedStateHandle?.get("category")
            val articleID: String? = navController.previousBackStackEntry?.savedStateHandle?.get("articleID")
            if (newsModel != null && category != null) {
                FeedScreen(navController, newsModel, category, articleID, isDarkMode, modifier)
            }
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