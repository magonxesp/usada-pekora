package com.usadapekora.bot.application.guild

import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.guild.GuildPreferencesException
import com.usadapekora.bot.domain.guild.GuildPreferencesRepository

class GuildPreferenceDeleter(private val repository: GuildPreferencesRepository) {

    fun delete(guildId: String, preference: GuildPreferences.GuildPreference) {
        repository.findByGuildId(guildId).getOrNull()?.let {
            it.preferences.remove(preference)
            repository.save(it)
        }
    }

}
