package com.usadapekora.bot.domain.trigger.exception

sealed class TriggerAudioException(override val message: String? = "") : Exception(message) {
    class NotFound(message: String? = "") : TriggerAudioException(message)
    class AlreadyExists(message: String? = "") : TriggerAudioException(message)
}
