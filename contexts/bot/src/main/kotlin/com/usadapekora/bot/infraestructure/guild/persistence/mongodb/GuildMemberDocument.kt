package com.usadapekora.bot.infraestructure.guild.persistence.mongodb

import com.usadapekora.bot.domain.guild.GuildMember
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class GuildMemberDocument(
    val _id: ObjectId? = null,
    var guildId: String? = null,
    var userId: String? = null,
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<GuildMember, GuildMemberDocument>({ GuildMemberDocument() }) {
        override fun fromEntity(
            entity: GuildMember,
            document: GuildMemberDocument
        ): GuildMemberDocument {
            document.guildId = entity.guild.value
            document.userId = entity.user.value

            return document
        }
    }

    fun toEntity() = GuildMember.fromPrimitives(
        user = userId!!,
        guild = guildId!!
    )
}
