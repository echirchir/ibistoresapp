package dev.chirchir.core.ui.base

import timber.log.Timber

object IBILogger {

    private const val TAG = "IBIAPP: "

    fun d(message: String) {
        Timber.d("$TAG $message")
    }

    fun e(message: String, throwable: Throwable? = null) {
        Timber.e(throwable, message)
    }

    fun i(message: String) {
        Timber.i("$TAG $message")
    }

    fun v(message: String) {
        Timber.v("$TAG $message")
    }

    fun w(message: String) {
        Timber.w("$TAG $message")
    }
}