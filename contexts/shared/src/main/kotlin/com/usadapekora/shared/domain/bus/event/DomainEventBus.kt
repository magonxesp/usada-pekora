package com.usadapekora.shared.domain.bus.event

import arrow.core.Either

interface DomainEventBus {
    fun dispatch(vararg events: DomainEvent): Either<DomainEventBusException, Unit>
}
