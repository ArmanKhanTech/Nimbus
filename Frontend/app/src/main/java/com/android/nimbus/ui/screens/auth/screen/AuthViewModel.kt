package com.android.nimbus.ui.screen.auth.screen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.android.nimbus.Screen
import com.android.nimbus.repository.AuthRepository
import com.android.nimbus.utility.SharedPreferenceUtility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class AuthViewModel(
    private val navController: NavController,
    private val sharedPreferences: SharedPreferenceUtility
) : ViewModel() {
    private val authRepository = AuthRepository(
        FirebaseAuth.getInstance(),
        FirebaseFirestore.getInstance()
    )

    var loading = MutableStateFlow(false)
    var errorMsg = MutableStateFlow("")

    fun signupWithEmail(
        email: String,
        password: String,
        name: String,
        phone: String
    ) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            handleErrorMsg("All fields are required")
            return
        }

        if (!email.contains("@")) {
            handleErrorMsg("Invalid email")
            return
        } else if (password.length < 6) {
            handleErrorMsg("Password must be at least 6 characters")
            return
        } else if (name.length < 3) {
            handleErrorMsg("Name must be at least 3 characters")
            return
        } else if (phone.length < 10) {
            handleErrorMsg("Phone number must be at least 10 characters")
            return
        }

        viewModelScope.launch {
            loading.value = true

            val result = authRepository
                .signupWithEmailAndPassword(email, password, name, phone)
                .first()

            if (result.result != null) {
                loading.value = false

                sharedPreferences.saveBooleanData("loggedIn", true)

                navController.navigate(Screen.HOME.name) {
                    launchSingleTop = true
                    popUpTo(Screen.HOME.name) {
                        inclusive = true
                    }
                }
            } else {
                loading.value = false
                handleErrorMsg(result.error)
            }
        }
    }

    fun loginWithEmail(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            handleErrorMsg("All fields are required")
            return
        }

        if (!email.contains("@")) {
            handleErrorMsg("Invalid email")
            return
        } else if (password.length < 6) {
            handleErrorMsg("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            loading.value = true

            val result = authRepository
                .loginWithEmailAndPassword(email, password)
                .first()

            if (result.result != null) {
                loading.value = false

                sharedPreferences.saveBooleanData("loggedIn", true)

                navController.navigate(Screen.HOME.name) {
                    launchSingleTop = true
                    popUpTo(Screen.HOME.name) {
                        inclusive = true
                    }
                }
            } else {
                loading.value = false
                handleErrorMsg(result.error)
            }
        }
    }

    fun sendResetPasswordEmail(email: String) {
        if (email.isEmpty()) {
            handleErrorMsg("Email is required")
            return
        }

        if (!email.contains("@")) {
            handleErrorMsg("Invalid email")
            return
        }

        viewModelScope.launch {
            loading.value = true

            val result = authRepository
                .sendResetPasswordEmail(email)
                .first()

            if (result.error == "none") {
                loading.value = false
                handleErrorMsg("Password reset email sent")
            } else {
                loading.value = false
                handleErrorMsg(result.error)
            }
        }
    }

    private fun handleErrorMsg(error: String) {
        errorMsg.value = error
        viewModelScope.launch {
            delay(2000)
            errorMsg.value = ""
        }
    }
}