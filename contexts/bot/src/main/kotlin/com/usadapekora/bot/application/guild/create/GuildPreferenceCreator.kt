package com.usadapekora.bot.application.guild.create

import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.guild.GuildPreferencesException
import com.usadapekora.bot.domain.guild.GuildPreferencesRepository

class GuildPreferenceCreator(private val repository: GuildPreferencesRepository) {

    fun create(guildId: String, preference: GuildPreferences.GuildPreference, value: String) {
        val preferences = repository.findByGuildId(guildId).getOrNull()
            ?: GuildPreferences(guildId)

        preferences.preferences[preference] = value
        repository.save(preferences)
    }

}
