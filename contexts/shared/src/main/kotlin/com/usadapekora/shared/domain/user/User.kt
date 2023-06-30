package com.usadapekora.shared.domain.user

import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.domain.valueobject.UuidValueObject

data class User(
    val id: UserId,
    val avatar: UserAvatar?,
    val name: UserName,
    val providerId: UserProviderId,
    val provider: UserProvider
) : Entity() {
    data class UserId(override val value: String) : UuidValueObject(value = value)
    data class UserAvatar(val value: String)
    data class UserName(val value: String)
    data class UserProviderId(val value: String)
    enum class UserProvider(val value: String) {
        DISCORD("discord");

        companion object {
            fun fromValue(value: String) = values().firstOrNull { it.value == value }
                ?: throw UserException.InvalidProvider("The provider $value is invalid")
        }
    }

    companion object {
        fun fromPrimitives(
            id: String,
            avatar: String?,
            name: String,
            providerId: String,
            provider: String
        ) = User(
            id = UserId(id),
            avatar = avatar?.let { UserAvatar(avatar) },
            name = UserName(name),
            providerId = UserProviderId(providerId),
            provider = UserProvider.fromValue(provider)
        )
    }

    override fun id(): String = id.value
}
