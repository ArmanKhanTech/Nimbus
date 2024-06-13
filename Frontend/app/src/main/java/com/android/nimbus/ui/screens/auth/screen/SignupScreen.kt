package com.android.nimbus.ui.screen.auth.screen

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
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.nimbus.ui.components.BottomComponent
import com.android.nimbus.ui.components.BottomSignupTextComponent
import com.android.nimbus.ui.components.CustomTextField
import com.android.nimbus.ui.components.FeedsAppBar
import com.android.nimbus.ui.components.HeadingTextComponent
import com.android.nimbus.ui.components.PasswordInputComponent
import com.android.nimbus.ui.components.SignupTermsAndPrivacyText

@Composable
fun SignupScreen(
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
                title = "Signup",
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
                HeadingTextComponent(heading = "Welcome Back")
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
                Spacer(modifier = modifier.height(20.dp))
                CustomTextField(
                    labelVal = "Full Name",
                    icon = {
                        Icon(
                            imageVector = Icons.Sharp.Person,
                            contentDescription = "Name Icon",
                            modifier = modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier
                )
                Spacer(modifier = modifier.height(20.dp))
                CustomTextField(
                    labelVal = "Mobile",
                    icon = {
                        Icon(
                            imageVector = Icons.Sharp.Phone,
                            contentDescription = "Mobile Icon",
                            modifier = modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier
                )
                Spacer(modifier = modifier.height(10.dp))
                SignupTermsAndPrivacyText()
                BottomComponent(
                    "Signup",
                    "Signup with Google",
                    actionButtonAction = {

                    },
                    googleButtonAction = {

                    },
                    navController,
                    modifier
                )
                Spacer(modifier = modifier.height(10.dp))
                BottomSignupTextComponent(navController)
            }
        }
    }
}