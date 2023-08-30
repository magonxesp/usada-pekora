package com.usadapekora.shared.domain.valueobject

import com.usadapekora.shared.domain.exception.InvalidUuidException
import java.util.*

open class UuidValueObject(open val value: String?) {
    protected fun validate() {
        try {
            if (value != null) {
                UUID.fromString(value)
            }
        } catch (exception: IllegalArgumentException) {
            throw InvalidUuidException(exception.message)
        }
    }
}
