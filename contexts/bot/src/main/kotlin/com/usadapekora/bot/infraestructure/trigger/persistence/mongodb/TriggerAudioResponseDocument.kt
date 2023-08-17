package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument

class TriggerAudioResponseDocument(
    val id: String = "",
    val trigger: String = "",
    val source: String = "",
    val sourceUri: String = "",
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerAudioResponse, TriggerAudioResponseDocument>({ TriggerAudioResponseDocument() }) {
        override fun fromEntity(entity: TriggerAudioResponse, document: TriggerAudioResponseDocument) = TriggerAudioResponseDocument(
            id = entity.id.value,
            source = entity.source.name,
            sourceUri = entity.sourceUri.value
        )
    }

    fun toEntity() = TriggerAudioResponse.fromPrimitives(
        id = id,
        source = source,
        sourceUri = sourceUri
    )
}
