package com.android.nimbus.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.android.nimbus.R

val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic)
)

val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    displayMedium = defaultTypography.displayMedium.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),
    displaySmall = defaultTypography.displaySmall.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    headlineLarge = defaultTypography.headlineLarge.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    headlineMedium = defaultTypography.headlineMedium.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    headlineSmall = defaultTypography.headlineSmall.copy(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
)