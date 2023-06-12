package com.usadapekora.shared.domain.valueobject

import kotlinx.datetime.Instant

open class DateTimeValueObject(open val value: Instant) {
    override fun equals(other: Any?): Boolean {
        return other is DateTimeValueObject && other.value.toEpochMilliseconds() == value.toEpochMilliseconds()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
