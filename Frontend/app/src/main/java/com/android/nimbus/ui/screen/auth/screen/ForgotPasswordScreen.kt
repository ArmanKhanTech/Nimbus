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
import androidx.compose.material.icons.sharp.LockReset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.nimbus.ui.component.CustomButton
import com.android.nimbus.ui.component.CustomTextField
import com.android.nimbus.ui.component.CustomToast
import com.android.nimbus.ui.component.FeedsAppBar
import com.android.nimbus.ui.component.HeadingTextComponent
import com.android.nimbus.ui.component.MessageType
import com.android.nimbus.ui.component.TextInfoComponent
import com.android.nimbus.utility.SharedPreferenceUtility

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sharedPreferences = SharedPreferenceUtility(context)
    val viewModel = AuthViewModel(navController, sharedPreferences)

    var email by rememberSaveable { mutableStateOf("") }

    val loading by viewModel.loading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

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
                .padding(innerPadding)
                .fillMaxSize(),
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
                Spacer(modifier = Modifier.height(10.dp))
                if (errorMsg.isNotEmpty()) {
                    if (errorMsg == "Password reset email sent") {
                        CustomToast(
                            message = "Password reset email sent",
                            messageType = MessageType.SUCCESS,
                            modifier = modifier
                        )
                    } else {
                        CustomToast(
                            message = errorMsg,
                            messageType = MessageType.ERROR,
                            modifier = modifier
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                TextInfoComponent(
                    textVal = "Please enter the email address associated with your account."
                )
                Spacer(modifier = Modifier.height(20.dp))
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
                CustomButton(
                    child = {
                        if (loading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = modifier.size(25.dp)
                            )
                        } else {
                            Text(
                                text = "Send Email",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    action = {
                        if (!loading) viewModel.sendResetPasswordEmail(email)
                    },
                    modifier = modifier
                )
            }
        }
    }
}