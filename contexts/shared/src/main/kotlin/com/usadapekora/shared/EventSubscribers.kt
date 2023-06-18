package com.usadapekora.shared

import com.usadapekora.shared.domain.bus.event.Event
import com.usadapekora.shared.domain.bus.event.EventSubscriber
import kotlin.reflect.KClass

typealias EventSubscribers = Array<KClass<EventSubscriber<Event>>>
