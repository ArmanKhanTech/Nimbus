package com.android.nimbus.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.KeyOff
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.nimbus.Screen

@Composable
fun ImageComponent(
    image: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .size(300.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun HeadingTextComponent(
    heading: String
) {
    Text(
        text = heading,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        textAlign = TextAlign.Start
    )
}

@Composable
fun CustomTextField(
    labelVal: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val typeOfKeyboard: KeyboardType = when (labelVal) {
        "Email ID" -> KeyboardType.Email
        "Mobile" -> KeyboardType.Phone
        else -> KeyboardType.Text
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(
                text = labelVal,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = icon,
        keyboardOptions = KeyboardOptions(
            keyboardType = typeOfKeyboard,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Composable
fun PasswordInputComponent(
    labelVal: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isShowPassword by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(
                text = labelVal,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Sharp.Lock,
                contentDescription = "Lock Icon",
                modifier = modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            val description = if (isShowPassword)
                "Show Password"
            else
                "Hide Password"
            val iconImage =
                if (isShowPassword)
                    Icons.Outlined.KeyOff
                else
                    Icons.Outlined.Key

            IconButton(onClick = {
                isShowPassword = !isShowPassword
            }) {
                Icon(
                    imageVector = iconImage,
                    contentDescription = description,
                    modifier = modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isShowPassword)
            VisualTransformation.None
        else
            PasswordVisualTransformation()
    )
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ForgotPasswordTextComponent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Forgot Password?",
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
            ) {
                navController.navigate(Screen.FORGOT_PASSWORD.name)
            },
        textAlign = TextAlign.End,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CustomButton(
    child: @Composable () -> Unit,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .height(50.dp)
    ) {
        child()
    }
}

@Composable
fun BottomLoginTextComponent(
    initialText: String,
    action: String,
    navController: NavController
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        ) {
            append(initialText)
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        ) {
            pushStringAnnotation(tag = action, annotation = action)
            append(action)
        }
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium,
        onClick = {
            annotatedString
                .getStringAnnotations(it, it)
                .firstOrNull()?.also { span ->
                    if (span.item == "Signup!") {
                        navController.navigate(Screen.SIGNUP.name)
                    }
                }
        }
    )
}

@Composable
fun SignupTermsAndPrivacyText() {
    val initialText = "Join us and accept our "
    val termsNConditionText = "Terms & Conditions"
    val andText = " and "
    val privacyPolicyText = "Privacy Policy."

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        ) {
            append(initialText)
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        ) {
            pushStringAnnotation(
                tag = termsNConditionText,
                annotation = termsNConditionText
            )
            append(termsNConditionText)
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        ) {
            append(andText)
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        ) {
            pushStringAnnotation(
                tag = privacyPolicyText,
                annotation = privacyPolicyText
            )
            append(privacyPolicyText)
        }
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium,
        onClick = {
            annotatedString
                .getStringAnnotations(it, it)
                .firstOrNull()?.also { span ->
                    Log.d("SignupTermsAndPrivacyText", span.item)
                }
        }
    )
}

@Composable
fun BottomSignupTextComponent(navController: NavController) {
    val initialText = "Are you a familiar? "
    val loginText = "Login"

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        ) {
            append(initialText)
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        ) {
            pushStringAnnotation(
                tag = loginText,
                annotation = loginText
            )
            append(loginText)
        }
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium,
        onClick = {
            annotatedString
                .getStringAnnotations(it, it)
                .firstOrNull()?.also { span ->
                    if (span.item == "Login") {
                        navController.navigate(Screen.LOGIN.name)
                    }
                }
        }
    )
}

@Composable
fun TextInfoComponent(
    textVal: String
) {
    Text(
        text = textVal,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyMedium,
    )
}