package com.usadapekora.shared.domain.bus.event

import arrow.core.Either

interface DomainEventConsumer {
    fun consume(): Either<DomainEventConsumerError, Unit>
}
