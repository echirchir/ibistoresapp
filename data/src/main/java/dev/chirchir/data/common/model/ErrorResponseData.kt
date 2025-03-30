package dev.chirchir.data.common.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseData(val message: String)