package es.magonxesp.pekorabot.modules.guild.infraestructure.persistence

import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferences
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesException
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesRepository
import es.magonxesp.pekorabot.mongoConnectionUrl
import es.magonxesp.pekorabot.mongoDatabase
import org.litote.kmongo.*

class MongoDbGuildPreferencesRepository : GuildPreferencesRepository {

    private val client = KMongo.createClient(mongoConnectionUrl)
    private val database = client.getDatabase(mongoDatabase)
    private val collection = database.getCollectionOfName<GuildPreferencesDocument>("guildPreferences")

    override fun findByGuildId(guildId: String): GuildPreferences {
        val guildPreferences = collection.findOne(GuildPreferencesDocument::guildId eq guildId)

        if (guildPreferences != null) {
            return guildPreferences.toAggregate()
        }

        throw GuildPreferencesException.NotFound("Guild preferences by guild id $guildId not found")
    }

    override fun findByPreference(preference: GuildPreferences.GuildPreference): Array<GuildPreferences> {
        val guildPreferences = collection.find(GuildPreferencesDocument::preferences.keyProjection(preference) exists true)
        return guildPreferences.map { it.toAggregate() }.toList().toTypedArray()
    }

    override fun save(guildPreferences: GuildPreferences) {
        val document = collection.findOne(GuildPreferencesDocument::guildId eq guildPreferences.guildId)

        if (document != null) {
            collection.updateOne(GuildPreferencesDocument.fromAggregate(guildPreferences, document))
        } else {
            collection.insertOne(GuildPreferencesDocument.fromAggregate(guildPreferences))
        }
    }

    override fun delete(guildPreferences: GuildPreferences) {
        collection.deleteOne(GuildPreferencesDocument::guildId eq guildPreferences.guildId)
    }
}
