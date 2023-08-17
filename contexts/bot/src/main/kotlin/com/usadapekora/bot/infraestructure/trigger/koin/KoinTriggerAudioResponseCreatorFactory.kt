package com.usadapekora.bot.infraestructure.trigger.koin

import arrow.core.Either
import arrow.core.left
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.shared.serviceContainer

class KoinTriggerAudioResponseCreatorFactory : TriggerAudioResponseCreatorFactory {
    override fun getInstance(content: TriggerAudioResponseContent): Either<TriggerAudioResponseException.CreatorNotAvailable, TriggerAudioResponseCreator> =
        Either.catch {
            when(content) {
                is TriggerAudioResponseFileContent -> serviceContainer().get<TriggerAudioResponseFileSourceCreator>()
                else -> return TriggerAudioResponseException.CreatorNotAvailable("Creator not available for ${content::class} instance").left()
            }
        }.mapLeft { TriggerAudioResponseException.CreatorNotAvailable(it.message) }
}
