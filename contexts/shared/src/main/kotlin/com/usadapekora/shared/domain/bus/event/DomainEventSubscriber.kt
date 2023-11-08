package com.usadapekora.shared.domain.bus.event

import arrow.core.Either

interface DomainEventSubscriber<T : DomainEvent> {
    fun handle(event: T): Either<DomainEventSubscriberException, Unit>
}
