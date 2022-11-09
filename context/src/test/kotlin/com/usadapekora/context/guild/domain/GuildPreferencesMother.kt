package com.usadapekora.context.guild.domain

import com.usadapekora.context.guild.domain.GuildPreferences
import java.util.Random

class GuildPreferencesMother {
    companion object {
        fun create(
            guildId: String? = null,
            preferences: MutableMap<GuildPreferences.GuildPreference, String> = mutableMapOf()
        ) = GuildPreferences(
            guildId = guildId ?: Random().nextLong().toString(),
            preferences = preferences
        )
    }
}
