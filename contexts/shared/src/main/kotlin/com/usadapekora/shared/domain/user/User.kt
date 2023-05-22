package com.usadapekora.shared.domain.user

import com.usadapekora.shared.domain.common.Entity
import com.usadapekora.shared.domain.valueobject.UuidValueObject

data class User(
    val id: UserId,
    val avatar: UserAvatar?,
    val name: UserName,
    val discordId: DiscordUserId
) : Entity() {
    data class UserId(override val value: String) : UuidValueObject(value = value)
    data class UserAvatar(val value: String)
    data class UserName(val value: String)
    data class DiscordUserId(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            avatar: String?,
            name: String,
            discordId: String
        ) = User(
            id = UserId(id),
            avatar = avatar?.let { UserAvatar(avatar) },
            name = UserName(name),
            discordId = DiscordUserId(discordId)
        )
    }

    override fun id(): String = id.value
}
