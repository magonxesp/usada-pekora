package com.usadapekora.shared

import kotlin.reflect.KClass

typealias DomainEvents = Map<String, KClass<*>>
typealias DomainEventSubscribers = Map<String, Array<KClass<*>>>
