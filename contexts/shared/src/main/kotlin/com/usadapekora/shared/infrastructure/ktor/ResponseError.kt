package com.usadapekora.shared.infrastructure.ktor

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val error: String,
)
