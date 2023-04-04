package com.usadapekora.bot.domain

import com.usadapekora.bot.domain.guild.GuildPreferences
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
