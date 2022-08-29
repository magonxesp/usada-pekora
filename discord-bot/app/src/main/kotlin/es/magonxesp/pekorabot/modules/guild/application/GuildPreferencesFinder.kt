package es.magonxesp.pekorabot.modules.guild.application

import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesRepository

class GuildPreferencesFinder(private val repository: GuildPreferencesRepository) {

    fun find(guildId: String) = repository.findByGuildId(guildId)

}
