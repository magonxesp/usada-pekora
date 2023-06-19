package com.usadapekora.shared.infrastructure.bus.event.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import com.usadapekora.shared.domain.bus.event.EventConsumed
import com.usadapekora.shared.domain.bus.event.EventProcessed
import com.usadapekora.shared.domain.bus.event.EventProcessedError
import com.usadapekora.shared.domain.bus.event.EventProcessedRepository
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbEventProcessedRepository : MongoDbRepository<EventProcessed, EventProcessedDocument>(
    collection = "eventProcessed",
    documentIdProp = EventProcessedDocument::id,
    documentCompanion = EventProcessedDocument.Companion
), EventProcessedRepository {
    override fun find(
        id: EventProcessed.EventProcessedId,
        consumedBy: EventConsumed.EventConsumedBy
    ): Either<EventProcessedError.NotFound, EventProcessed> = Either.catch {
        val eventProcessed = oneQuery<EventProcessedDocument>(collection) { collection ->
            collection.findOne(and(EventProcessedDocument::id eq id.value, EventProcessedDocument::consumedBy eq consumedBy.value) )
        } ?: return EventProcessedError.NotFound("EventProcessed with id ${id.value} not found").left()
        eventProcessed.toEntity()
    }.mapLeft { EventProcessedError.NotFound(it.message) }

    override fun save(entity: EventProcessed): Either<EventProcessedError.SaveError, Unit> = Either.catch {
        performSave(entity)
    }.mapLeft { EventProcessedError.SaveError(it.message) }
}
