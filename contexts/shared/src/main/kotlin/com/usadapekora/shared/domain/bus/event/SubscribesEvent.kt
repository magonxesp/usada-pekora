package com.usadapekora.shared.domain.bus.event

import kotlin.reflect.KClass

annotation class SubscribesEvent(val event: KClass<Event>)
