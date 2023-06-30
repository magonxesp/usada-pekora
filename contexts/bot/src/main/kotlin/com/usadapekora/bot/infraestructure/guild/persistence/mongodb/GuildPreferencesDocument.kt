package com.usadapekora.bot.infraestructure.guild.persistence.mongodb

import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument

class GuildPreferencesDocument(
    var guildId: String? = null,
    var preferences: MutableMap<GuildPreferences.GuildPreference, String> = mutableMapOf()
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<GuildPreferences, GuildPreferencesDocument>({ GuildPreferencesDocument() }) {
        override fun fromEntity(
            entity: GuildPreferences,
            document: GuildPreferencesDocument
        ): GuildPreferencesDocument {
            document.guildId = entity.guildId
            document.preferences = entity.preferences

            return document
        }
    }

    fun toEntity() = GuildPreferences(
        guildId = guildId!!,
        preferences = preferences
    )
}
