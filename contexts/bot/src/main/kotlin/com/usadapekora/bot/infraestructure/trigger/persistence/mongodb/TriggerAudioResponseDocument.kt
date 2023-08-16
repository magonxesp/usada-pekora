package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument

class TriggerAudioResponseDocument(
    val id: String = "",
    val trigger: String = "",
    val guild: String = "",
    val file: String = "",
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerAudioResponse, TriggerAudioResponseDocument>({ TriggerAudioResponseDocument() }) {
        override fun fromEntity(entity: TriggerAudioResponse, document: TriggerAudioResponseDocument) = TriggerAudioResponseDocument(
            id = entity.id.value,
            trigger = entity.trigger.value,
            guild = entity.guild.value,
            file = entity.sourceUri.value
        )
    }

    fun toEntity() = TriggerAudioResponse.fromPrimitives(
        id = id,
        trigger = trigger,
        guild = guild,
        sourceUri = file
    )
}
