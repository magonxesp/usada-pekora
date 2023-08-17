package com.usadapekora.bot.infraestructure.trigger.fake

import arrow.core.Either
import arrow.core.right
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseContent
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseWriter

class FakeTriggerAudioResponseWriter : TriggerAudioResponseWriter {
    override fun write(responseAudio: TriggerAudioResponse, content: TriggerAudioResponseContent): Either<TriggerAudioResponseException.FailedToWrite, Unit> = Unit.right()
}
