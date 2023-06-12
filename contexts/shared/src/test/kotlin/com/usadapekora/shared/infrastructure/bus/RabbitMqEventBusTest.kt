package com.usadapekora.shared.infrastructure.bus

import com.usadapekora.shared.domain.bus.Event
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.test.Test
import kotlin.test.assertIs

class RabbitMqEventBusTest {

    @Serializable
    class TestEvent(override val name: String = "test_event") : Event()

    val module = SerializersModule {
        polymorphic(Event::class) {
            subclass(TestEvent::class)
        }
    }

    @Test
    fun `it should dispatch an event`() {
        val bus = RabbitMqEventBus(module)
        val result = bus.dispatch(TestEvent())

        assertIs<Unit>(result.getOrNull())
    }

}
