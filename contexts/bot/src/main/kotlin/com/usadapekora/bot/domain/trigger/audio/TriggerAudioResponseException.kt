package com.usadapekora.bot.domain.trigger.audio

sealed class TriggerAudioResponseException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : TriggerAudioResponseException(message)
    class AlreadyExists(override val message: String? = null) : TriggerAudioResponseException(message)
    class FailedToSave(override val message: String? = null) : TriggerAudioResponseException(message)
    class FailedToDelete(override val message: String? = null) : TriggerAudioResponseException(message)
    class FailedToRead(override val message: String? = null) : TriggerAudioResponseException(message)
    class FailedToUpdate(override val message: String? = null) : TriggerAudioResponseException(message)
}
