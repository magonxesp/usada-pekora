package com.usadapekora.shared.domain.bus.event

import arrow.core.Either

interface EventSubscriber<T : Event> {
    fun handle(event: T): Either<EventSubscriberError, Unit>
}
