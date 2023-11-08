package com.usadapekora.shared.domain.bus.event

class DomainEventBusException(override val message: String? = null) : Exception(message)
