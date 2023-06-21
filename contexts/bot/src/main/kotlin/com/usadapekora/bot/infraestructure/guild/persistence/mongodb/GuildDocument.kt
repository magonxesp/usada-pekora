package com.usadapekora.bot.infraestructure.guild.persistence.mongodb

import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class GuildDocument(
    val _id: ObjectId? = null,
    var id: String? = null,
    var name: String? = null,
    var iconUrl: String? = null,
    var providerId: String? = null,
    var provider: String? = null,
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<Guild, GuildDocument>({ GuildDocument() }) {
        override fun fromEntity(
            entity: Guild,
            document: GuildDocument
        ): GuildDocument {
            document.id = entity.id.value
            document.name = entity.name.value
            document.iconUrl = entity.iconUrl.value
            document.providerId = entity.providerId.value
            document.provider = entity.provider.value

            return document
        }
    }

    fun toEntity() = Guild.fromPrimitives(
        id = id!!,
        name = name!!,
        iconUrl = iconUrl!!,
        providerId = providerId!!,
        provider = provider!!
    )
}
