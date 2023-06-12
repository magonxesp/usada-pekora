package com.usadapekora.shared.domain.bus

import arrow.core.Either

interface EventBus {
    fun dispatch(vararg events: Event): Either<EventBusError, Unit>
}
