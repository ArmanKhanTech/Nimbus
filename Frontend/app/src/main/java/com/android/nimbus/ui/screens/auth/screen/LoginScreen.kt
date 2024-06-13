package com.android.nimbus.ui.screen.auth.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AlternateEmail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.nimbus.R
import com.android.nimbus.ui.components.BottomComponent
import com.android.nimbus.ui.components.BottomLoginTextComponent
import com.android.nimbus.ui.components.CustomTextField
import com.android.nimbus.ui.components.ForgotPasswordTextComponent
import com.android.nimbus.ui.components.HeadingTextComponent
import com.android.nimbus.ui.components.ImageComponent
import com.android.nimbus.ui.components.PasswordInputComponent

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = modifier
                .scrollable(
                    state = rememberScrollState(),
                    orientation = Orientation.Vertical
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                ImageComponent(
                    image = R.drawable.app_icon,
                    modifier
                )
                HeadingTextComponent(heading = "Welcome to Nimbus")
                Spacer(modifier = modifier.height(20.dp))
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
                Spacer(modifier = modifier.height(20.dp))
                PasswordInputComponent(
                    labelVal = "Password",
                    modifier
                )
                Spacer(modifier = modifier.height(10.dp))
                ForgotPasswordTextComponent(
                    navController,
                    modifier
                )
                BottomComponent(
                    "Login",
                    "Login with Google",
                    actionButtonAction = {

                    },
                    googleButtonAction = {

                    },
                    navController,
                    modifier
                )
                Spacer(modifier = modifier.height(12.dp))
                BottomLoginTextComponent(
                    initialText = "Haven't we seen you around here before? ",
                    action = "Signup!",
                    navController
                )
            }
        }
    }
}