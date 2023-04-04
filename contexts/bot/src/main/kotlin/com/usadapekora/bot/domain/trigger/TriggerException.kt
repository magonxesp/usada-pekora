package com.usadapekora.bot.domain.trigger

sealed class TriggerException(override val message: String? = null) : Exception(message) {
    class InvalidValue(override val message: String? = null) : TriggerException(message)
    class NotFound(override val message: String? = null) : TriggerException(message)
    class AlreadyExists(override val message: String? = null) : TriggerException(message)
}
