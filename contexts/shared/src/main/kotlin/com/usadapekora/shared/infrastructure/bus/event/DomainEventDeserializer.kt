package com.usadapekora.shared.infrastructure.bus.event

import com.usadapekora.shared.domain.bus.event.DomainEvent
import com.usadapekora.shared.domain.bus.event.DomainEventFactory
import com.usadapekora.shared.infrastructure.serialization.createJacksonObjectMapperInstance
import kotlinx.datetime.Instant
import kotlin.reflect.full.companionObjectInstance

class DomainEventDeserializer(private val registry: DomainEventRegistry) {
    private val objectMapper = createJacksonObjectMapperInstance()

    fun deserialize(rawEvent: String): DomainEvent {
        val decodedEvent = objectMapper.readValue(rawEvent, DomainEventJson::class.java)
        val registeredEvents = registry.events
        val eventClass = registeredEvents.filter { it.key == decodedEvent.name }
            .map { it.value }
            .firstOrNull() ?: throw RuntimeException("Domain Event class for event ${decodedEvent.name} not found")

        if (eventClass.companionObjectInstance == null || eventClass.companionObjectInstance !is DomainEventFactory<*>) {
            throw RuntimeException("The domain event $eventClass should have companion object that implements ${DomainEventFactory::class} interface")
        }

        return (eventClass.companionObjectInstance as DomainEventFactory<DomainEvent>).fromPrimitives(
            id = decodedEvent.id,
            name = decodedEvent.name,
            occurredOn = Instant.parse(decodedEvent.occurredOn),
            attributes = decodedEvent.attributes
        )
    }

}
