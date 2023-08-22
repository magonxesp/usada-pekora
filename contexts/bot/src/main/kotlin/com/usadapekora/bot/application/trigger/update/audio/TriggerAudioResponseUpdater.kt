package com.usadapekora.bot.application.trigger.update.audio

import arrow.core.Either
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileWriter

class TriggerAudioResponseUpdater(
    private val repository: TriggerAudioResponseRepository,
    private val fileWriter: DomainFileWriter,
    private val fileDeleter: DomainFileDeleter
) {
    fun update(request: TriggerAudioResponseUpdateRequest): Either<TriggerAudioResponseException, Unit> {
        TODO("implement")
    }
}
