package com.usadapekora.context.guild.domain

import java.util.Random

object GuildPreferencesMother {
    fun create(
        guildId: String? = null,
        preferences: MutableMap<GuildPreferences.GuildPreference, String> = mutableMapOf()
    ) = GuildPreferences(
        guildId = guildId ?: Random().nextLong().toString(),
        preferences = preferences
    )
}
