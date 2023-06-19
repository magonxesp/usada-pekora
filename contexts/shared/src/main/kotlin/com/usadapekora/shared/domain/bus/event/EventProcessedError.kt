package com.usadapekora.shared.domain.bus.event

sealed class EventProcessedError(open val message: String? = null) {
    class NotFound(override val message: String? = null) : EventProcessedError(message = message)
    class SaveError(override val message: String? = null) : EventProcessedError(message = message)
}
