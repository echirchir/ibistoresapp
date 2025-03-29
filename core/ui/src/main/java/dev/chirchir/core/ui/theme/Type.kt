package dev.chirchir.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dev.chirchir.core.ui.R

private val regular = Font(R.font.outfit_regular, FontWeight.W400)
private val medium = Font(R.font.outfit_medium, FontWeight.W500)
private val semiBold = Font(R.font.outfit_semi_bold, FontWeight.W600)
private val bold = Font(R.font.outfit_bold, FontWeight.W700)

// Create a font family to use in TextStyles
private val outfitFontFamily = FontFamily(regular, medium, semiBold, bold)

// Set of Material typography styles to start with
val IBITypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = outfitFontFamily,
    ),
    bodyMedium = TextStyle(
        fontFamily = outfitFontFamily,
    ),
    bodySmall = TextStyle(
        fontFamily = outfitFontFamily,
    ),
    displayLarge = TextStyle(
        fontFamily = outfitFontFamily,
    ),
    displayMedium = TextStyle(
        fontFamily = outfitFontFamily,
    ),
    displaySmall = TextStyle(
        fontFamily = outfitFontFamily,
    ),
)