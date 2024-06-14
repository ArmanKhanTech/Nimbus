package com.android.nimbus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.android.nimbus.ui.theme.NimbusTheme
import com.android.nimbus.utility.SharedPreferenceUtility
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseApp.initializeApp(this)

            val sharedPreferences = SharedPreferenceUtility(this)
            val isDarkMode =
                remember { mutableStateOf(sharedPreferences.getBooleanData("darkMode", false)) }

            NimbusTheme(
                useDarkTheme = isDarkMode.value
            ) {
                NimbusApp(isDarkMode = isDarkMode)
            }
        }
    }
}