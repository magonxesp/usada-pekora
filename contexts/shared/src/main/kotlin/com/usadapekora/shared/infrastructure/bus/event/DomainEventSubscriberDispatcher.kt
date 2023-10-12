package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.bus.event.DomainEvent
import com.usadapekora.shared.domain.bus.event.DomainEventSubscriber
import com.usadapekora.shared.domain.bus.event.DomainEventSubscriberError
import com.usadapekora.shared.domain.bus.event.SubscribesDomainEvent
import com.usadapekora.shared.domain.getAnnotation
import org.koin.java.KoinJavaComponent
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class DomainEventSubscriberDispatcher(
    private val registry: DomainEventRegistry,
    loggerFactory: LoggerFactory
) {
    private val logger = loggerFactory.getLogger(this::class)

    private fun getSubscribersOfEvent(eventName: String): Array<KClass<*>> {
        val subscribers = registry.subscribers[eventName] ?: arrayOf()

        subscribers.forEach { subscriberClass ->
            if (!subscriberClass.isSubclassOf(DomainEventSubscriber::class)) {
                throw RuntimeException("The domain event subscriber $subscriberClass should implement ${DomainEventSubscriber::class}")
            }
        }

        return subscribers
    }

    private fun executeSubscriber(event: DomainEvent, subscribeClass: KClass<*>): Either<DomainEventSubscriberError, Unit> {
        val subscribesTo = getAnnotation<SubscribesDomainEvent>(subscribeClass)

        if (!subscribesTo.eventClass.isInstance(event)) {
            throw RuntimeException("The given event instance is not valid for $subscribeClass got ${event::class} and expects ${subscribesTo::class}")
        }

        val subscriber: DomainEventSubscriber<DomainEvent> by KoinJavaComponent.inject(subscribeClass.java)

        return subscriber.handle(event).onRight {
            logger.info("Domain event ${event.name} with id ${event.id} successfully handled")
        }.onLeft {
            logger.warning("Failed to handle event ${event.name} with id ${event.id}: ${it.message}")
        }
    }

    fun dispatch(event: DomainEvent): Either<DomainEventSubscriberError, Unit> {
        getSubscribersOfEvent(event.name).forEach { subscriberClass ->
            executeSubscriber(event, subscriberClass).onLeft {
                return it.left()
            }
        }

        return Unit.right()
    }
}
