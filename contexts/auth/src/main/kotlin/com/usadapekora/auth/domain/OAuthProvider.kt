package com.usadapekora.auth.domain

enum class OAuthProvider(val value: String) {
    DISCORD("discord");

    companion object {
        fun fromValue(value: String)
            = values().firstOrNull { it.value == value}
                ?: throw OAuthProviderException.NotFound("The provider $value is invalid")
    }
}
