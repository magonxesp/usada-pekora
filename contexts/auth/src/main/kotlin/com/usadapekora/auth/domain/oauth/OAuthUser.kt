package com.usadapekora.auth.domain.oauth

import java.util.*

data class OAuthUser(
    val id: String,
    val name: String? = null,
    val avatar: String? = null,
    val token: String,
    val nextDomainUserId: String = UUID.randomUUID().toString()
)
