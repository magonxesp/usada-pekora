package com.usadapekora.shared.domain.bus.event

import arrow.core.Either

interface EventConsumedRepository {
    fun find(id: EventConsumed.EventConsumedId, consumedBy: EventConsumed.EventConsumedBy): Either<EventConsumedError.NotFound, EventConsumed>
    fun save(entity: EventConsumed): Either<EventConsumedError.SaveError, Unit>
}
