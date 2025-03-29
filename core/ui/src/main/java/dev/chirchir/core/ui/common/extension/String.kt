package dev.chirchir.core.ui.common.extension

import android.text.TextUtils
import android.util.Patterns

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.matchesPasswordRegex(): Boolean {
    val regex = Constants.PASSWORD_VALIDATION_REGEX.toRegex()
    return regex.matches(this)
}