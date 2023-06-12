package com.usadapekora.shared.infrastructure.ktor

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class ResponseError(
    val error: String,
)
