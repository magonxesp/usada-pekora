package com.usadapekora.bot.domain.trigger

sealed class TriggerException(override val message: String? = null) : Exception(message) {
    class InvalidValue(override val message: String? = null) : TriggerException(message)
    class NotFound(override val message: String? = null) : TriggerException(message)
    class AlreadyExists(override val message: String? = null) : TriggerException(message)
    class MissingResponse(override val message: String? = null) : TriggerException(message)
    class MissingAudioProvider(override val message: String? = null) : TriggerException(message)
    class InvalidKind(override val message: String? = null) : TriggerException(message)
    class UnsupportedKind(override val message: String? = null) : TriggerException(message)
}
