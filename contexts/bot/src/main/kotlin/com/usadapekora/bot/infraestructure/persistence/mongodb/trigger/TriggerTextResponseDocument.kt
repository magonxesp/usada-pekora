package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.domain.trigger.TriggerTextResponse
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class TriggerTextResponseDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val content: String = "",
    val type: String = "",
    val triggerId: String = "",
    val discordGuildId: String = ""
) : MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerTextResponse, TriggerTextResponseDocument>(TriggerTextResponseDocument()) {
        override fun fromEntity(entity: TriggerTextResponse, document: TriggerTextResponseDocument) = TriggerTextResponseDocument(
            _id = document._id,
            id = entity.id.value,
            content = entity.content.value,
            type = entity.type.value,
            triggerId = entity.triggerId.value,
            discordGuildId = entity.discordGuildId.value
        )
    }

    fun toEntity() = TriggerTextResponse.fromPrimitives(
        id = id,
        content = content,
        type = type,
        triggerId = triggerId,
        discordGuildId = discordGuildId
    )
}
