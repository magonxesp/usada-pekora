package com.usadapekora.bot.infraestructure.guild.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.guild.GuildPreferencesException
import com.usadapekora.bot.domain.guild.GuildPreferencesRepository
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.*

class MongoDbGuildPreferencesRepository : MongoDbRepository<GuildPreferences, GuildPreferencesDocument>(
    collection = "guildPreferences",
    documentIdProp = GuildPreferencesDocument::guildId,
    documentCompanion = GuildPreferencesDocument
), GuildPreferencesRepository {

    override fun findByGuildId(guildId: String): Either<GuildPreferencesException.NotFound, GuildPreferences> {
        val guildPreferences = oneQuery<GuildPreferencesDocument>("guildPreferences") { collection ->
            collection.findOne(GuildPreferencesDocument::guildId eq guildId)
        }

        if (guildPreferences != null) {
            return guildPreferences.toEntity().right()
        }

        return GuildPreferencesException.NotFound("Guild preferences by guild id $guildId not found").left()
    }

    override fun findByPreference(preference: GuildPreferences.GuildPreference): Array<GuildPreferences> {
        val guildPreferences = collectionQuery<GuildPreferencesDocument>("guildPreferences") { collection ->
            collection.find(GuildPreferencesDocument::preferences.keyProjection(preference) exists true)
        }

        return guildPreferences.map { it.toEntity() }.toList().toTypedArray()
    }

    override fun save(entity: GuildPreferences) {
        performSave(entity)
    }

    override fun delete(entity: GuildPreferences) {
        performDelete(entity)
    }
}
