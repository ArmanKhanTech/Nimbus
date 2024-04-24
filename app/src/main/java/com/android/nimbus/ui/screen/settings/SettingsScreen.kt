package com.android.nimbus.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.android.nimbus.ui.components.MediumAppBar
import com.android.nimbus.utility.SharedPreferenceUtility

@Composable
fun SettingsScreen(
    navController: NavController,
    isDarkMode: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val sharedPreferences = SharedPreferenceUtility(LocalContext.current)

    Scaffold(
        topBar = {
            MediumAppBar(
                title = "Settings",
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            ListItem(
                headlineContent = {
                    Text(
                        "Dark Mode",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground

                    )
                },
                supportingContent = {
                    Text(
                        "Toggle Dark Mode",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.NightsStay,
                        contentDescription = "Dark Mode",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    Switch(
                        checked = isDarkMode.value,
                        onCheckedChange = {
                            sharedPreferences.saveBooleanData("darkMode", it)
                            isDarkMode.value = it
                        }
                    )
                }
            )
        }
    }
}
