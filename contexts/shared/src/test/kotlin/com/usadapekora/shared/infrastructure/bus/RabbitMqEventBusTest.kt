package com.usadapekora.shared.infrastructure.bus

import com.usadapekora.shared.domain.bus.Event
import kotlin.test.Test
import kotlin.test.assertIs

class RabbitMqEventBusTest {

    class TestEvent(override val name: String = "test_event") : Event()

    @Test
    fun `it should dispatch an event`() {
        val bus = RabbitMqEventBus()
        val result = bus.dispatch(TestEvent())

        assertIs<Unit>(result.getOrNull())
    }

}
