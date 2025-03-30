package dev.chirchir.domain.common.exception

object InternetNotAvailableException : Exception("Internet is not available") {
    private fun readResolve(): Any = InternetNotAvailableException
}