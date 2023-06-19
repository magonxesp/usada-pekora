package com.usadapekora.shared.domain.bus.event

import arrow.core.Either

interface EventProcessedRepository {
    fun find(id: EventProcessed.EventProcessedId, consumedBy: EventProcessed.EventProcessedConsumedBy): Either<EventProcessedError.NotFound, EventProcessed>
    fun save(entity: EventProcessed): Either<EventProcessedError.SaveError, Unit>
}
