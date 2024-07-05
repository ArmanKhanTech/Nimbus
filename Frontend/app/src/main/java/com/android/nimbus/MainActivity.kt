package com.android.nimbus

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.android.nimbus.ui.theme.NimbusTheme
import com.android.nimbus.ui.viewmodel.SharedViewModel
import com.android.nimbus.utility.SharedPreferenceUtility
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseApp.initializeApp(this)

            val sharedPreferences = SharedPreferenceUtility(this)
            val isDarkMode = remember {
                    mutableStateOf(
                    sharedPreferences.getBooleanData(
                        "darkMode",
                        false)
                    )
                }

            val city = sharedPreferences.getStringData("city", "")
            if(!city.isNullOrEmpty()) {
                SharedViewModel.setCity(city)
            }

            NimbusTheme(
                useDarkTheme = isDarkMode.value
            ) {
                NimbusApp(isDarkMode = isDarkMode)
            }
        }
    }
}