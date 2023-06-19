package com.usadapekora.shared.domain.bus.event

sealed class EventConsumedError(open val message: String? = null) {
    class NotFound(override val message: String? = null) : EventConsumedError(message = message)
    class SaveError(override val message: String? = null) : EventConsumedError(message = message)
}
