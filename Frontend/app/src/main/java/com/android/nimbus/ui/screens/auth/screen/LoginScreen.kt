package com.android.nimbus.ui.screen.auth.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AlternateEmail
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.nimbus.R
import com.android.nimbus.ui.components.BottomLoginTextComponent
import com.android.nimbus.ui.components.CustomButton
import com.android.nimbus.ui.components.CustomTextField
import com.android.nimbus.ui.components.ForgotPasswordTextComponent
import com.android.nimbus.ui.components.HeadingTextComponent
import com.android.nimbus.ui.components.ImageComponent
import com.android.nimbus.ui.components.MessageType
import com.android.nimbus.ui.components.PasswordInputComponent
import com.android.nimbus.ui.components.TopToast

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = AuthViewModel(navController)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loading = viewModel.loading.collectAsState().value
    val errorMsg = viewModel.errorMsg.collectAsState().value

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .scrollable(
                    state = rememberScrollState(),
                    orientation = Orientation.Vertical
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageComponent(
                image = R.drawable.app_icon,
                modifier
            )
            HeadingTextComponent(heading = "Welcome to Nimbus")
            Spacer(modifier = modifier.height(20.dp))
            if (errorMsg.isNotEmpty()) {
                TopToast(
                    message = errorMsg,
                    messageType = MessageType.ERROR,
                    modifier = modifier
                )
            }
            CustomTextField(
                labelVal = "Email ID",
                value = email,
                onValueChange = {
                    email = it
                },
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
                value = password,
                onValueChange = {
                    password = it
                },
                modifier
            )
            Spacer(modifier = modifier.height(10.dp))
            ForgotPasswordTextComponent(
                navController,
                modifier
            )
            CustomButton(
                child = {
                    if (loading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Signup",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                },
                action = {
                    if (!loading) viewModel.loginWithEmail(email, password)
                },
                modifier = modifier
            )
            Spacer(modifier = modifier.height(15.dp))
            BottomLoginTextComponent(
                initialText = "Haven't we seen you around here before? ",
                action = "Signup!",
                navController
            )
        }
    }
}