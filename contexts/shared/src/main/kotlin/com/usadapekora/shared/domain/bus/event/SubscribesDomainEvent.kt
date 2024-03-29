package com.usadapekora.shared.domain.bus.event

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class SubscribesDomainEvent(val eventClass: KClass<*>)
