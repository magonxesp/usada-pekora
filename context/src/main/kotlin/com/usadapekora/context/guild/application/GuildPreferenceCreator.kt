package com.usadapekora.context.guild.application

import com.usadapekora.context.guild.domain.GuildPreferences
import com.usadapekora.context.guild.domain.GuildPreferencesException
import com.usadapekora.context.guild.domain.GuildPreferencesRepository

class GuildPreferenceCreator(private val repository: GuildPreferencesRepository) {

    fun create(guildId: String, preference: GuildPreferences.GuildPreference, value: String) {
        val preferences = try {
            repository.findByGuildId(guildId)
        } catch (exception: GuildPreferencesException.NotFound) {
            GuildPreferences(guildId)
        }

        preferences.preferences[preference] = value
        repository.save(preferences)
    }

}
