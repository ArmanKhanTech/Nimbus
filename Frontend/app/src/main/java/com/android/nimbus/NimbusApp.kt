package com.android.nimbus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.nimbus.ui.screen.auth.screen.LoginScreen
import com.android.nimbus.ui.screen.auth.screen.SignupScreen
import com.android.nimbus.ui.screen.feed.FeedScreen
import com.android.nimbus.ui.screen.home.HomeScreen
import com.android.nimbus.ui.screen.settings.SettingsScreen
import com.android.nimbus.ui.screen.splash.SplashScreen
import com.android.nimbus.ui.screens.auth.screen.ForgotPasswordScreen
import com.android.nimbus.ui.screens.bookmarks.BookmarksScreen
import com.android.nimbus.ui.screens.search.SearchScreen
import com.android.nimbus.utility.scaleInTransition
import com.android.nimbus.utility.scaleOutTransition

enum class Screen {
    SPLASH,
    HOME,
    FEED,
    SETTINGS,
    LOGIN,
    SIGNUP,
    FORGOT_PASSWORD,
    SEARCH,
    BOOKMARKS
}

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object Feed : NavigationItem(Screen.FEED.name)
    data object Settings : NavigationItem(Screen.SETTINGS.name)
    data object Login : NavigationItem(Screen.LOGIN.name)
    data object Signup : NavigationItem(Screen.SIGNUP.name)
    data object ForgotPassword : NavigationItem(Screen.FORGOT_PASSWORD.name)
    data object Search : NavigationItem(Screen.SEARCH.name)
    data object Bookmarks : NavigationItem(Screen.BOOKMARKS.name)
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
        startDestination = NavigationItem.Splash.route,
        enterTransition = {
            scaleInTransition()
        },
        exitTransition = {
            scaleOutTransition()
        },
        popEnterTransition = {
            scaleInTransition()
        },
        popExitTransition = {
            scaleOutTransition()
        }
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen(navController, modifier)
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(navController, isDarkMode, modifier)
        }
        composable(
            "${NavigationItem.Feed.route}/{articleID}&{category}",
            arguments = listOf(
                navArgument("articleID") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType }
            )
        ) {
            val articleID = it.arguments?.getString("articleID")
            val category = it.arguments?.getString("category")
            FeedScreen(navController, articleID, category!!, isDarkMode, modifier)
        }
        composable(
            "${NavigationItem.Feed.route}/{category}",
            arguments = listOf(
                navArgument("category") { type = NavType.StringType }
            )
        ) {
            val category = it.arguments?.getString("category")
            FeedScreen(navController, null, category!!, isDarkMode, modifier)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(navController, isDarkMode, modifier)
        }
        composable(NavigationItem.Login.route) {
            LoginScreen(navController, modifier)
        }
        composable(NavigationItem.Signup.route) {
            SignupScreen(navController, modifier)
        }
        composable(NavigationItem.ForgotPassword.route) {
            ForgotPasswordScreen(navController, modifier)
        }
        composable(NavigationItem.Search.route) {
            SearchScreen(navController, modifier)
        }
        composable(NavigationItem.Bookmarks.route) {
            BookmarksScreen(navController, modifier)
        }
    }
}