package com.usadapekora.shared.application.user.find

import com.usadapekora.shared.domain.user.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val avatar: String?,
    val name: String,
    val providerId: String,
    val provider: String
) {
    companion object {
        fun fromEntity(user: User) = UserResponse(
            id = user.id.value,
            avatar = user.avatar?.value,
            name = user.name.value,
            providerId = user.providerId.value,
            provider = user.provider.value
        )
    }
}
