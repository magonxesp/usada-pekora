package com.usadapekora.context.domain.trigger

sealed class TriggerAudioException(override val message: String?) : Exception(message) {
    class NotFound(message: String?) : TriggerAudioException(message)
}
