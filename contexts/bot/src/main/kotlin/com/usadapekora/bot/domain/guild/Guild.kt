package com.usadapekora.bot.domain.guild

import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.domain.valueobject.UuidValueObject

data class Guild(
    val id: GuildId,
    var name: GuildName,
    var iconUrl: GuildIconUrl,
    val providerId: GuildProviderId,
    val provider: GuildProvider
) : Entity() {
    data class GuildId(override val value: String) : UuidValueObject(value = value)
    data class GuildName(val value: String)
    data class GuildIconUrl(val value: String)
    data class GuildProviderId(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            name: String,
            iconUrl: String,
            providerId: String,
            provider: String
        ) = Guild(
            id = GuildId(id),
            name = GuildName(name),
            iconUrl = GuildIconUrl(iconUrl),
            providerId = GuildProviderId(providerId),
            provider = GuildProvider.fromValue(provider),
        )
    }

    override fun id() = id.value
}
