package com.usadapekora.shared.application.user.create

data class UserCreateRequest(
    val id: String,
    val avatar: String?,
    val name: String,
    val providerId: String,
    val provider: String
)
