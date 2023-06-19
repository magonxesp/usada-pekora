package com.usadapekora.shared.domain.bus.event

import arrow.core.Either

interface EventProcessedRepository {
    fun find(id: EventProcessed.EventProcessedId, consumedBy: EventConsumed.EventConsumedBy): Either<EventProcessedError.NotFound, EventProcessed>
    fun save(entity: EventProcessed): Either<EventProcessedError.SaveError, Unit>
}
