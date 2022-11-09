package com.usadapekora.context.guild.infraestructure.persistence

import com.usadapekora.context.guild.domain.GuildPreferences
import com.usadapekora.context.guild.domain.GuildPreferencesException
import com.usadapekora.context.guild.domain.GuildPreferencesRepository
import com.usadapekora.context.shared.infraestructure.persistence.MongoDbRepository
import org.litote.kmongo.*

class MongoDbGuildPreferencesRepository : MongoDbRepository(), GuildPreferencesRepository {

    override fun findByGuildId(guildId: String): GuildPreferences {
        val guildPreferences = oneQuery<GuildPreferencesDocument>("guildPreferences") { collection ->
            collection.findOne(GuildPreferencesDocument::guildId eq guildId)
        }

        if (guildPreferences != null) {
            return guildPreferences.toAggregate()
        }

        throw GuildPreferencesException.NotFound("Guild preferences by guild id $guildId not found")
    }

    override fun findByPreference(preference: GuildPreferences.GuildPreference): Array<GuildPreferences> {
        val guildPreferences = collectionQuery<GuildPreferencesDocument>("guildPreferences") { collection ->
            collection.find(GuildPreferencesDocument::preferences.keyProjection(preference) exists true)
        }

        return guildPreferences.map { it.toAggregate() }.toList().toTypedArray()
    }

    override fun save(guildPreferences: GuildPreferences) {
        writeQuery<GuildPreferencesDocument>("guildPreferences") { collection ->
            val document = collection.findOne(GuildPreferencesDocument::guildId eq guildPreferences.guildId)

            if (document != null) {
                collection.updateOne(GuildPreferencesDocument.fromAggregate(guildPreferences, document))
            } else {
                collection.insertOne(GuildPreferencesDocument.fromAggregate(guildPreferences))
            }
        }
    }

    override fun delete(guildPreferences: GuildPreferences) {
        writeQuery<GuildPreferencesDocument>("guildPreferences") { collection ->
            collection.deleteOne(GuildPreferencesDocument::guildId eq guildPreferences.guildId)
        }
    }
}
