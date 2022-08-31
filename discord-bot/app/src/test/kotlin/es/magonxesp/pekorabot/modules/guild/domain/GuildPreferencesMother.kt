package es.magonxesp.pekorabot.modules.guild.domain

import java.util.Random
import java.util.UUID

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
