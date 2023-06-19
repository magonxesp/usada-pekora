package com.usadapekora.shared.domain.bus.event

import com.usadapekora.shared.EventSubscribers

interface EventConsumer {
    fun startConsume(subscribers: EventSubscribers)
}
