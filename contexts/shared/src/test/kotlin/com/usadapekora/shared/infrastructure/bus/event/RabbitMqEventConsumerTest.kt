package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import arrow.core.right
import com.usadapekora.shared.DependencyInjectionEnabledTest
import com.usadapekora.shared.domain.bus.event.*
import com.usadapekora.shared.infrastructure.bus.event.persistence.mongodb.MongoDbEventProcessedRepository
import com.usadapekora.shared.infrastructure.bus.persistence.redis.RedisEventConsumedRepository
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import org.koin.dsl.module
import java.util.UUID
import kotlin.test.*

class RabbitMqEventConsumerTest : DependencyInjectionEnabledTest() {

    var handledEvents = mutableListOf<String>()

    @EventName("test_event")
    class TestEvent(val exampleId: String = UUID.randomUUID().toString()) : Event()

    @SubscribesEvent<TestEvent>(eventClass = TestEvent::class)
    inner class TestSubscriber : EventSubscriber<TestEvent> {
        override fun handle(event: TestEvent): Either<EventSubscriberError, Unit> {
            handledEvents += event.exampleId
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
    }

    @Test
    fun `it should consume the test event`() = runBlocking {
        handledEvents = mutableListOf()
        val event = TestEvent()
        val result = bus.dispatch(event)
        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)

        val consumerResult = consumer.consume(arrayOf(TestSubscriber::class))
        assertIs<Unit>(consumerResult.getOrNull(), result.leftOrNull()?.message)
        Thread.sleep(1000)
        assertContentEquals(listOf(event.exampleId), handledEvents)
    }

    @Test
    fun `it should consume the test event avoiding race conditions and duplicates`() = runBlocking {
        handledEvents = mutableListOf()
        val events = (1..100).map { TestEvent() }

        val result = bus.dispatch(*events.toTypedArray())
        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)

        (1..3).forEach { _ ->
            consumer.consume(arrayOf(TestSubscriber::class)).run {
                assertIs<Unit>(getOrNull(), leftOrNull()?.message)
            }
        }

        Thread.sleep(2000)
        val exampleIds = events.map { it.exampleId }.toMutableList()
        assertContentEquals(exampleIds, handledEvents)
    }

}
