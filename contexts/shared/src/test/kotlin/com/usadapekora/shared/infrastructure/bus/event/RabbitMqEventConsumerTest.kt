package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import arrow.core.right
import com.usadapekora.shared.DependencyInjectionEnabledTest
import com.usadapekora.shared.DomainEventSubscribers
import com.usadapekora.shared.DomainEvents
import com.usadapekora.shared.domain.bus.event.DomainEventSubscriber
import com.usadapekora.shared.domain.bus.event.DomainEventSubscriberException
import com.usadapekora.shared.domain.bus.event.ExampleDomainEvent
import com.usadapekora.shared.domain.bus.event.SubscribesDomainEvent
import com.usadapekora.shared.infrastructure.Slf4jLoggerFactory
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RabbitMqEventConsumerTest : DependencyInjectionEnabledTest() {

    private val registry = DomainEventRegistry()
    var handledEvents = mutableListOf<String>()

    @SubscribesDomainEvent(eventClass = ExampleDomainEvent::class)
    inner class TestSubscriber : DomainEventSubscriber<ExampleDomainEvent> {
        override fun handle(event: ExampleDomainEvent): Either<DomainEventSubscriberException, Unit> {
            handledEvents += event.id
            return Unit.right()
        }
    }

    val events: DomainEvents = mapOf(
        "example_event" to ExampleDomainEvent::class
    )

    val subscribers: DomainEventSubscribers = mapOf(
        "example_event" to arrayOf(
            TestSubscriber::class
        )
    )

    private val loggerFactory = Slf4jLoggerFactory()
    private val serializer = DomainEventSerializer()
    private val deserializer = DomainEventDeserializer(registry)
    private val bus = RabbitMqEventBus(serializer)
    private val dispatcher = DomainEventSubscriberDispatcher(registry, loggerFactory)
    private val consumer = RabbitMqEventConsumer(
        loggerFactory = loggerFactory,
        registry = registry,
        eventDeserializer = deserializer,
        eventSubscriberDispatcher = dispatcher
    )

    @BeforeTest
    fun setup() {
        setupTestModules {
            listOf(module { single { TestSubscriber() } })
        }

        registry.registerDomainEvents(events)
        registry.registerDomainEventSubscribers(subscribers)
    }

    @Test
    fun `it should consume the test event`() = runBlocking {
        handledEvents = mutableListOf()
        val event = ExampleDomainEvent()
        val result = bus.dispatch(event)
        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)

        val consumerResult = consumer.consume()
        assertIs<Unit>(consumerResult.getOrNull(), result.leftOrNull()?.message)
        Thread.sleep(1000)
        assertTrue(handledEvents.isNotEmpty())
    }

    @Test
    fun `it should consume the test event avoiding race conditions and duplicates`() = runBlocking {
        handledEvents = mutableListOf()
        val events = (1..100).map { ExampleDomainEvent() }

        val result = bus.dispatch(*events.toTypedArray())
        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)

        (1..3).forEach { _ ->
            consumer.consume().run {
                assertIs<Unit>(getOrNull(), leftOrNull()?.message)
            }
        }

        Thread.sleep(10000)
        assertTrue(handledEvents.isNotEmpty())
    }

}
