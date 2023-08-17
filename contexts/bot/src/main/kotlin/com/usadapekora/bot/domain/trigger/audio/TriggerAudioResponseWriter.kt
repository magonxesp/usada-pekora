package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either

interface TriggerAudioResponseWriter {
    fun write(responseAudio: TriggerAudioResponse, content: TriggerAudioResponseContent): Either<TriggerAudioResponseException.FailedToWrite, Unit>
}
