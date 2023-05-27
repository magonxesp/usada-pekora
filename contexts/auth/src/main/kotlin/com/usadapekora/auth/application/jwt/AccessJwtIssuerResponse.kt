package com.usadapekora.auth.application.jwt

import kotlinx.serialization.Serializable

@Serializable
data class AccessJwtIssuerResponse(
    val accessToken: String,
    val expiresAt: Long
)
