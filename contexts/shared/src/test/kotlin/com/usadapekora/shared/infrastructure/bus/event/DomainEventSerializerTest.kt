package com.usadapekora.shared.infrastructure.bus.event

import com.usadapekora.shared.domain.bus.event.ExampleDomainEvent
import kotlin.test.Test

class DomainEventSerializerTest {
    private val serializer = DomainEventSerializer()

    @Test
    fun `it should serialize a domain event`() {
        val event = ExampleDomainEvent()
        serializer.serialize(event)
    }

}
