package com.usadapekora.context.trigger.domain

sealed class TriggerAudioException(override val message: String?) : Exception(message) {
    class NotFound(message: String?) : TriggerAudioException(message)
}
