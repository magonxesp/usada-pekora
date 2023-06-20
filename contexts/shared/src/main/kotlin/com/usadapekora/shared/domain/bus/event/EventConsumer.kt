package com.usadapekora.shared.domain.bus.event

import arrow.core.Either
import com.usadapekora.shared.EventSubscribers

interface EventConsumer {
    fun consume(subscribers: EventSubscribers): Either<EventConsumerError, Unit>
}
