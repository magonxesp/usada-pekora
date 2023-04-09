package com.usadapekora.bot.infraestructure.persistence.mongodb.guild

import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class GuildPreferencesDocument(
    val _id: ObjectId? = null,
    var guildId: String? = null,
    var preferences: MutableMap<GuildPreferences.GuildPreference, String> = mutableMapOf()
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<GuildPreferences, GuildPreferencesDocument>(GuildPreferencesDocument()) {
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