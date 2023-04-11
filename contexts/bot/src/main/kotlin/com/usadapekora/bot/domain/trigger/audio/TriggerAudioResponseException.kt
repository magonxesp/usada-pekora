package com.usadapekora.bot.domain.trigger.audio

sealed class TriggerAudioResponseException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : TriggerAudioResponseException(message)
    class AlreadyExists(override val message: String? = null) : TriggerAudioResponseException(message)
}