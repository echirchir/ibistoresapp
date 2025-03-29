package dev.chirchir.core.ui.common.extension

import com.google.gson.Gson

fun Any.toJson(): String? {
    try {
        return Gson().toJson(this)
    } catch (e: Exception) {
        e.stackTrace
    }
    return null
}