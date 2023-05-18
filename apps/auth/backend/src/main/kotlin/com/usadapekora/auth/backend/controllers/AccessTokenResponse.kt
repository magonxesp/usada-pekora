package com.usadapekora.auth.backend.controllers

data class AccessTokenResponse(
    val accessToken: String,
    val expires: Long
)
