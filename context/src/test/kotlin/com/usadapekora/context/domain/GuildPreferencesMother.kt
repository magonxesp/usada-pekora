package com.usadapekora.context.domain

import com.usadapekora.context.domain.guild.GuildPreferences
import java.util.Random

object GuildPreferencesMother : ObjectMother<GuildPreferences> {
    fun create(
        guildId: String? = null,
        preferences: MutableMap<GuildPreferences.GuildPreference, String> = mutableMapOf()
    ) = GuildPreferences(
        guildId = guildId ?: Random().nextLong().toString(),
        preferences = preferences
    )

    override fun random(): GuildPreferences = create()
}
