package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either

interface TriggerAudioResponseWriterFactory {
    fun getInstance(content: TriggerAudioResponseContent): Either<TriggerAudioResponseException.WriterNotAvailable, TriggerAudioResponseWriter>
}
