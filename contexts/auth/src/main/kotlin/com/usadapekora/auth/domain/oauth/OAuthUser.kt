package com.usadapekora.auth.domain.oauth

data class OAuthUser(
    val id: String,
    val name: String? = null,
    val avatar: String? = null,
    val token: String
)
