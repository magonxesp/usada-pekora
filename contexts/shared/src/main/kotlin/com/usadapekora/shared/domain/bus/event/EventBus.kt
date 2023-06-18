package com.usadapekora.shared.domain.bus.event

import arrow.core.Either

interface EventBus {
    fun dispatch(vararg events: Event): Either<EventBusError, Unit>
}
