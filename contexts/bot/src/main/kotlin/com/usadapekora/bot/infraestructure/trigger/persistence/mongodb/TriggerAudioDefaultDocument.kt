package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class TriggerAudioDefaultDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val trigger: String = "",
    val guild: String = "",
    val file: String = "",
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerDefaultAudioResponse, TriggerAudioDefaultDocument>({ TriggerAudioDefaultDocument() }) {
        override fun fromEntity(entity: TriggerDefaultAudioResponse, document: TriggerAudioDefaultDocument) = TriggerAudioDefaultDocument(
            _id = document._id,
            id = entity.id.value,
            trigger = entity.trigger.value,
            guild = entity.guild.value,
            file = entity.file.value
        )
    }

    fun toEntity() = TriggerDefaultAudioResponse.fromPrimitives(
        id = id,
        trigger = trigger,
        guild = guild,
        file = file
    )
}
