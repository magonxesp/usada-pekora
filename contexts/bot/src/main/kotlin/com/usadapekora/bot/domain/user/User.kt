package com.usadapekora.bot.domain.user

import com.usadapekora.bot.domain.shared.Entity
import com.usadapekora.bot.domain.shared.valueobject.UuidValueObject

data class User(
    val id: UserId,
    val discordId: DiscordUserId
) : Entity() {
    data class UserId(override val value: String) : UuidValueObject(value = value)
    data class DiscordUserId(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            discordId: String
        ) = User(
            id = UserId(id),
            discordId = DiscordUserId(discordId)
        )
    }

    override fun id(): String = id.value
}
