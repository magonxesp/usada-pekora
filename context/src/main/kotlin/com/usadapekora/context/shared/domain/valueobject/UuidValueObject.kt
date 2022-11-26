package com.usadapekora.context.shared.domain.valueobject

import com.usadapekora.context.shared.domain.exception.InvalidUuidException
import java.util.UUID

open class UuidValueObject(open val value: String?) {
    init {
        validate()
    }

    fun validate() {
        try {
            if (value != null) {
                UUID.fromString(value)
            }
        } catch (exception: IllegalArgumentException) {
            throw InvalidUuidException(exception.message)
        }
    }
}
