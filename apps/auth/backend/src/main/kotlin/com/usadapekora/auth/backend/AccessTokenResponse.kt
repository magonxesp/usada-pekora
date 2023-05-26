package com.usadapekora.auth.backend

data class AccessTokenResponse(
    val accessToken: String,
    val expires: Long
)
private fun String.trimKey(): String
    = split("\n")
    .filter { !Regex("^-+[A-Z ]+-+\$").matches(it) }
    .joinToString("")
