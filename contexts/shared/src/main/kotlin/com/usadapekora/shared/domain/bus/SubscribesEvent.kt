package com.usadapekora.shared.domain.bus

import kotlin.reflect.KClass

annotation class SubscribesEvent(val event: KClass<Event>)
