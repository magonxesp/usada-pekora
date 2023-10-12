package com.usadapekora.shared.infrastructure.bus.event

import com.usadapekora.shared.DomainEvents
import com.usadapekora.shared.domain.bus.event.ExampleDomainEvent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DomainEventDeserializerTest {
    private val events: DomainEvents = mapOf(
        "example_event" to ExampleDomainEvent::class
    )

    private val registry = DomainEventRegistry()
    private val deserializer = DomainEventDeserializer(registry)
    private val serializer = DomainEventSerializer()

    @Test
    fun `it should deserialize a domain event`() {
        val event = ExampleDomainEvent()

        registry.registerDomainEvents(events)
        val serializedEvent = serializer.serialize(event)
        val deserializedEvent = deserializer.deserialize(serializedEvent)

        assertIs<ExampleDomainEvent>(deserializedEvent)
        assertEquals(event, deserializedEvent)
    }

}
