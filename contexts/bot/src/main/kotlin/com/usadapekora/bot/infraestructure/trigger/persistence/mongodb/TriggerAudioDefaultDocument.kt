package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument

class TriggerAudioDefaultDocument(
    val id: String = "",
    val trigger: String = "",
    val guild: String = "",
    val file: String = "",
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerDefaultAudioResponse, TriggerAudioDefaultDocument>({ TriggerAudioDefaultDocument() }) {
        override fun fromEntity(entity: TriggerDefaultAudioResponse, document: TriggerAudioDefaultDocument) = TriggerAudioDefaultDocument(
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
