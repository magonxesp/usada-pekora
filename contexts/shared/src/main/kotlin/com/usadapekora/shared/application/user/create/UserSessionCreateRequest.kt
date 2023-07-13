package com.usadapekora.shared.application.user.create

import kotlinx.datetime.Instant

class UserSessionCreateRequest(
    val id: String,
    val userId: String,
    val state: String,
    val expiresAt: Instant,
    val lastActiveAt: Instant,
    val device: String,
)
