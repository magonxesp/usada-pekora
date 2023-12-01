package com.usadapekora.shared.infrastructure.bus.event

import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.bus.event.ExampleDomainEvent
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertIs

class RabbitMqEventBusTest {

    @Test
    fun `it should dispatch an event`() {
        val serializer = DomainEventSerializer()
        val loggerFactory = mockk<LoggerFactory>()
        val bus = RabbitMqEventBus(serializer, loggerFactory)
        val result = bus.dispatch(ExampleDomainEvent())

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

}
