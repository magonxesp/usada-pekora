package com.usadapekora.shared.infrastructure.bus.event.persistence.mongodb

import com.usadapekora.shared.domain.bus.event.EventProcessed
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument
import kotlinx.datetime.Instant

class EventProcessedDocument(
    var id: String = "",
    var name: String = "",
    var consumedBy: String = "",
    var consumedOn: String = "",
    var timeElapsedMilliseconds: Long = 0
) : MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<EventProcessed, EventProcessedDocument>({ EventProcessedDocument() }) {
        override fun fromEntity(entity: EventProcessed, document: EventProcessedDocument): EventProcessedDocument {
            document.id = entity.id.value
            document.name = entity.name.value
            document.consumedBy = entity.consumedBy.value
            document.consumedOn = entity.consumedOn.value.toString()
            document.timeElapsedMilliseconds = entity.timeElapsedMilliseconds.value

            return document
        }
    }

    fun toEntity() = EventProcessed.fromPrimitives(
        id = id,
        name = name,
        consumedBy = consumedBy,
        consumedOn = Instant.parse(consumedOn),
        timeElapsedMilliseconds = timeElapsedMilliseconds
    )
}
