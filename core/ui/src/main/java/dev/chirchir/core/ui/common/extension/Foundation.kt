package dev.chirchir.core.ui.common.extension

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

fun Any.toJson(): String? {
    try {
        return Gson().toJson(this)
    } catch (e: Exception) {
        e.stackTrace
    }
    return null
}

inline fun <reified T : Any> String.fromJson(): T? {
    return try {
        Gson().fromJson(this, object : TypeToken<T>() {}.type)
    } catch (e: JsonSyntaxException) {
        e.printStackTrace()
        null
    }
}