package com.usadapekora.auth.domain.shared

import com.usadapekora.shared.domain.valueobject.UuidValueObject

class AuthenticatedUser(
    val id: AuthenticatedUserId,
    val avatarUrl: AuthenticatedUserAvatarUrl,
    val name: AuthenticatedUserName,
) {
    data class AuthenticatedUserId(override val value: String?) : UuidValueObject(value)
    data class AuthenticatedUserAvatarUrl(val value: String)
    data class AuthenticatedUserName(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            avatarUrl: String,
            name: String
        ) = AuthenticatedUser(
            id = AuthenticatedUserId(id),
            avatarUrl = AuthenticatedUserAvatarUrl(avatarUrl),
            name = AuthenticatedUserName(name)
        )
    }
}
