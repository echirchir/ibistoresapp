package dev.chirchir.ibistores

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import java.util.Locale


val LocalLocale = staticCompositionLocalOf { Locale.getDefault() }

@Composable
fun LocaleProvider(locale: Locale, content: @Composable () -> Unit) {
    val configuration = remember { Configuration() }
    configuration.setLocale(locale)

    CompositionLocalProvider(
        LocalLocale provides locale,
        content = content
    )
}