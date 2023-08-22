package com.usadapekora.bot

import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreator
import com.usadapekora.bot.application.trigger.delete.audio.TriggerAudioResponseDeleter
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUpdater
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileWriter
import io.mockk.mockk

abstract class TriggerModuleUnitTestCase : TestCase() {

    // Audio related services mocks
    protected val responseAudioRepository = mockk<TriggerAudioResponseRepository>(relaxed = true)
    protected val domainFileWriter = mockk<DomainFileWriter>(relaxUnitFun = true)
    protected val domainFileDeleter = mockk<DomainFileDeleter>(relaxUnitFun = true)

    protected val creator = TriggerAudioResponseCreator(responseAudioRepository, domainFileWriter)
    protected val updater = TriggerAudioResponseUpdater(responseAudioRepository, domainFileWriter, domainFileDeleter)
    protected val deleter = TriggerAudioResponseDeleter(responseAudioRepository, domainFileDeleter)

}
