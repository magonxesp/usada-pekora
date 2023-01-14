package com.usadapekora.context.infraestructure.persistence.mongodb.guild

import com.usadapekora.context.domain.guild.GuildPreferences
import com.usadapekora.context.domain.guild.GuildPreferencesException
import com.usadapekora.context.domain.guild.GuildPreferencesRepository
import com.usadapekora.context.infraestructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.*

class MongoDbGuildPreferencesRepository : MongoDbRepository<GuildPreferences, GuildPreferencesDocument>(
    collection = "guildPreferences",
    documentIdProp = GuildPreferencesDocument::guildId,
    documentCompanion = GuildPreferencesDocument.Companion
), GuildPreferencesRepository {

    override fun findByGuildId(guildId: String): GuildPreferences {
        val guildPreferences = oneQuery<GuildPreferencesDocument>("guildPreferences") { collection ->
            collection.findOne(GuildPreferencesDocument::guildId eq guildId)
        }

        if (guildPreferences != null) {
            return guildPreferences.toEntity()
        }

        throw GuildPreferencesException.NotFound("Guild preferences by guild id $guildId not found")
    }

    override fun findByPreference(preference: GuildPreferences.GuildPreference): Array<GuildPreferences> {
        val guildPreferences = collectionQuery<GuildPreferencesDocument>("guildPreferences") { collection ->
            collection.find(GuildPreferencesDocument::preferences.keyProjection(preference) exists true)
        }

        return guildPreferences.map { it.toEntity() }.toList().toTypedArray()
    }

}
