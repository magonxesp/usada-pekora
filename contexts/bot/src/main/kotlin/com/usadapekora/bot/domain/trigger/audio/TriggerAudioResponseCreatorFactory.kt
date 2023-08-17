package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either

interface TriggerAudioResponseCreatorFactory {
    fun getInstance(content: TriggerAudioResponseContent): Either<TriggerAudioResponseException.CreatorNotAvailable, TriggerAudioResponseCreator>
}
