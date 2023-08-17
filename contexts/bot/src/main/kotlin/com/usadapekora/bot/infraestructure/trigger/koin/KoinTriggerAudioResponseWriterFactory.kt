package com.usadapekora.bot.infraestructure.trigger.koin

import arrow.core.Either
import arrow.core.left
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.bot.infraestructure.trigger.fake.FakeTriggerAudioResponseWriter
import com.usadapekora.bot.infraestructure.trigger.filesystem.FileSystemTriggerAudioResponseWriter
import com.usadapekora.shared.serviceContainer

class KoinTriggerAudioResponseWriterFactory : TriggerAudioResponseWriterFactory {
    override fun getInstance(content: TriggerAudioResponseContent): Either<TriggerAudioResponseException.WriterNotAvailable, TriggerAudioResponseWriter> =
        Either.catch {
            when (content) {
                is TriggerAudioResponseFileContent -> serviceContainer().get<FileSystemTriggerAudioResponseWriter>()
                else -> serviceContainer().get<FakeTriggerAudioResponseWriter>()
            }
        }.mapLeft { TriggerAudioResponseException.WriterNotAvailable(it.message) }
}
