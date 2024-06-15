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
import com.android.nimbus.ui.components.BottomSignupTextComponent
import com.android.nimbus.ui.components.CustomButton
import com.android.nimbus.ui.components.CustomTextField
import com.android.nimbus.ui.components.CustomToast
import com.android.nimbus.ui.components.FeedsAppBar
import com.android.nimbus.ui.components.HeadingTextComponent
import com.android.nimbus.ui.components.MessageType
import com.android.nimbus.ui.components.PasswordInputComponent
import com.android.nimbus.ui.components.SignupTermsAndPrivacyText
import com.android.nimbus.utility.SharedPreferenceUtility

@Composable
fun SignupScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sharedPreferences = SharedPreferenceUtility(context)
    val viewModel = AuthViewModel(navController, sharedPreferences)

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }

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
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                HeadingTextComponent(heading = "Welcome Back")
                Spacer(modifier = modifier.height(10.dp))
                if (errorMsg.isNotEmpty()) {
                    CustomToast(
                        message = errorMsg,
                        messageType = MessageType.ERROR,
                        modifier = modifier
                    )
                }
                Spacer(modifier = modifier.height(10.dp))
                CustomTextField(
                    labelVal = "Email ID",
                    value = email,
                    onValueChange = { email = it },
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
                    onValueChange = { password = it },
                    modifier
                )
                Spacer(modifier = modifier.height(20.dp))
                CustomTextField(
                    labelVal = "Full Name",
                    value = name,
                    onValueChange = { name = it },
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
                    value = phone,
                    onValueChange = { phone = it },
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
                CustomButton(
                    child = {
                        if (loading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = modifier.size(25.dp)
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
                        if (!loading) viewModel.signupWithEmail(email, password, name, phone)
                    },
                    modifier = modifier
                )
                Spacer(modifier = modifier.height(15.dp))
                BottomSignupTextComponent(navController)
            }
        }
    }
}