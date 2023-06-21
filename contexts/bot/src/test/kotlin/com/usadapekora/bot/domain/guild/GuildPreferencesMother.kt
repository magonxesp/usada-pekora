package com.usadapekora.bot.domain.guild

import com.usadapekora.bot.domain.ObjectMother
import java.util.*

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
