package com.android.nimbus.ui.screens.auth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AlternateEmail
import androidx.compose.material.icons.sharp.LockReset
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.nimbus.ui.components.CustomButton
import com.android.nimbus.ui.components.CustomTextField
import com.android.nimbus.ui.components.FeedsAppBar
import com.android.nimbus.ui.components.HeadingTextComponent
import com.android.nimbus.ui.components.TextInfoComponent

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        topBar = {
            FeedsAppBar(
                title = "Password Reset",
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .scrollable(
                    state = rememberScrollState(),
                    orientation = Orientation.Vertical
                )
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Spacer(modifier = modifier.height(25.dp))
                Icon(
                    imageVector = Icons.Sharp.LockReset,
                    contentDescription = "Email Icon",
                    modifier = modifier.size(250.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(10.dp))
                HeadingTextComponent(heading = "Forgot Password")
                Spacer(modifier = Modifier.height(20.dp))
                TextInfoComponent(
                    textVal = "Please enter the email address associated with your account."
                )
                Spacer(modifier = Modifier.height(20.dp))
                CustomTextField(
                    labelVal = "Email ID",
                    icon = {
                        Icon(
                            imageVector = Icons.Sharp.AlternateEmail,
                            contentDescription = "Email Icon",
                            modifier = modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier
                )
                CustomButton(
                    labelVal = "Submit",
                    action = {
                        // Handle submit action
                    },
                    navController
                )
            }
        }
    }
}