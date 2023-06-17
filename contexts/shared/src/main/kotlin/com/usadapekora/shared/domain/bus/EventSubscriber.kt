package com.usadapekora.shared.domain.bus

import arrow.core.Either

interface EventSubscriber<T : Event> {
    fun handle(event: T): Either<EventSubscriberError, Unit>
}
