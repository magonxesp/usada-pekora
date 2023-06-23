package com.usadapekora.shared.infrastructure.bus.event

import com.usadapekora.shared.domain.bus.event.Event
import com.usadapekora.shared.domain.bus.event.EventName
import kotlin.test.Test
import kotlin.test.assertIs

class RabbitMqEventBusTest {

    @EventName("test_event")
    class TestEvent(val testValue: String = "example") : Event()

    @Test
    fun `it should dispatch an event`() {
        val bus = RabbitMqEventBus()
        val result = bus.dispatch(TestEvent())

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

}
