package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either

interface TriggerAudioResponseCreator {
    fun create(
        id: String,
        triggerId: String,
        guildId: String,
        content: TriggerAudioResponseContent
    ): Either<TriggerAudioResponseException.FailedToCreate, TriggerAudioResponse>
}
