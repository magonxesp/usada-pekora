package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument

class TriggerAudioResponseDocument(
    val id: String = "",
    val kind: String = "",
    val source: String = "",
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerAudioResponse, TriggerAudioResponseDocument>({ TriggerAudioResponseDocument() }) {
        override fun fromEntity(entity: TriggerAudioResponse, document: TriggerAudioResponseDocument) = TriggerAudioResponseDocument(
            id = entity.id.value,
            kind = entity.kind.name.lowercase(),
            source = entity.source.value
        )
    }

    fun toEntity() = TriggerAudioResponse.fromPrimitives(
        id = id,
        kind = kind,
        source = source
    )
}
