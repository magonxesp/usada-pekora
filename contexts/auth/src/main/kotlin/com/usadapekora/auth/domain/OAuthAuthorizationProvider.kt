package com.usadapekora.auth.domain

interface OAuthAuthorizationProvider {
    fun authorizeUrl(): String
}
