package com.usadapekora.shared.application.user.find

import kotlinx.serialization.Serializable

@Serializable
class UserResponse(
    val id: String,
    val avatar: String?,
    val name: String,
    val providerId: String,
    val provider: String
)
