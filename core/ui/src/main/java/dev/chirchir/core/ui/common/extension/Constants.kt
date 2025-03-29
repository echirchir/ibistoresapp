package dev.chirchir.core.ui.common.extension

object Constants {
    const val PASSWORD_VALIDATION_REGEX = """.*(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}.*"""
    const val EMAIL_VALIDATION_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
}