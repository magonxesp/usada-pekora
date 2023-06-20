package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.DependencyInjectionEnabledTest
import com.usadapekora.shared.domain.bus.event.*
import com.usadapekora.shared.infrastructure.bus.event.persistence.mongodb.MongoDbEventProcessedRepository
import com.usadapekora.shared.infrastructure.bus.event.persistence.redis.RedisEventConsumedRepository
import io.ktor.test.dispatcher.*
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import org.koin.dsl.module
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RabbitMqEventConsumerTest : DependencyInjectionEnabledTest() {

    var consumed = false

    @EventName("test_event")
    class TestEvent(val testValue: String = "example") : Event()

    @SubscribesEvent<TestEvent>(eventClass = TestEvent::class)
    inner class TestSubscriber : EventSubscriber<TestEvent> {
        override fun handle(event: TestEvent): Either<EventSubscriberError, Unit> {
            consumed = true
            return Unit.right()
        }
    }

    private val bus = RabbitMqEventBus()
    private val consumer = RabbitMqEventConsumer(
        eventConsumedRepository = RedisEventConsumedRepository(),
        eventProcessedRepository = MongoDbEventProcessedRepository(),
        clock = Clock.System
    )

    @BeforeTest
    fun setup() {
        setupTestModules {
            listOf(module { single { TestSubscriber() } })
        }
        consumed = false
    }

    @Test
    fun `it should consume the test event`() = testSuspend {
        val result = bus.dispatch(TestEvent())
        assertIs<Unit>(result.getOrNull())

        val consumerResult = consumer.startConsume(arrayOf(TestSubscriber::class))
        assertIs<Unit>(consumerResult.getOrNull())
        delay(1000)
        assertTrue(consumed)
    }

}
