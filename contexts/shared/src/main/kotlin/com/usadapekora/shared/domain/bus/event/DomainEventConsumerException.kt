package com.usadapekora.shared.domain.bus.event

class DomainEventConsumerException(override val message: String? = null) : Exception(message)
