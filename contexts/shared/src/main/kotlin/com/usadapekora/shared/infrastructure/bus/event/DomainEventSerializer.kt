package com.usadapekora.shared.infrastructure.bus.event

import com.usadapekora.shared.domain.bus.event.DomainEvent
import com.usadapekora.shared.infrastructure.serialization.createJacksonObjectMapperInstance

class DomainEventSerializer {
    private val objectMapper = createJacksonObjectMapperInstance()

    fun serialize(event: DomainEvent): String =
        objectMapper.writeValueAsString(DomainEventJson.fromDomainEvent(event))
}
