package com.usadapekora.auth.domain.jwt

sealed class JwtTokenError(val message: String? = null)
