package com.android.nimbus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.nimbus.ui.theme.NimbusTheme
import com.android.nimbus.utility.SharedPreferenceUtility

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = SharedPreferenceUtility(this)
        val isDarkTheme = sharedPreferences.getBooleanData("darkMode", false)

        setContent {
            NimbusTheme(
                isDarkTheme = isDarkTheme
            ) {
                NimbusApp()
            }
        }
    }
}