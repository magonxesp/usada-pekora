package es.magonxesp.pekorabot.modules.guild.infraestructure.persistence

import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferences
import org.bson.types.ObjectId

class GuildPreferencesDocument(
    val _id: ObjectId? = null,
    var guildId: String? = null,
    var preferences: MutableMap<GuildPreferences.GuildPreference, Any> = mutableMapOf()
) {
    companion object {
        fun fromAggregate(
            aggregate: GuildPreferences,
            document: GuildPreferencesDocument = GuildPreferencesDocument()
        ): GuildPreferencesDocument {
            document.guildId = aggregate.guildId
            document.preferences = aggregate.preferences

            return document
        }
    }

    fun toAggregate() = GuildPreferences(
        guildId = guildId!!,
        preferences = preferences
    )
}
