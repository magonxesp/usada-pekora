package com.usadapekora.shared.domain.bus

import com.usadapekora.shared.domain.valueobject.UuidValueObject
import kotlinx.datetime.Instant

data class EventId(override val value: String) : UuidValueObject(value = value)
data class EventName(val value: String)
data class EventConsumedBy(val value: String)
data class EventConsumedOn(val value: Instant)
data class EventTimeElapsedMilliseconds(val value: Long)
