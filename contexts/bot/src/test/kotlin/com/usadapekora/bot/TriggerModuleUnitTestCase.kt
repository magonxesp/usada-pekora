package com.usadapekora.bot

import arrow.core.right
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreator
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUpdater
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseCreator as TriggerAudioResponseCreatorDomainService
import com.usadapekora.bot.domain.trigger.audio.*
import io.mockk.every
import io.mockk.mockk

abstract class TriggerModuleUnitTestCase : TestCase() {

    // Audio related services mocks
    protected val responseAudioRepository = mockk<TriggerAudioResponseRepository>(relaxed = true)
    protected val writerFactory = mockk<TriggerAudioResponseWriterFactory>()
    protected val creatorFactory = mockk<TriggerAudioResponseCreatorFactory>()
    protected val writer = mockk<TriggerAudioResponseWriter>()
    protected val creatorDomainService = mockk<TriggerAudioResponseCreatorDomainService>()

    protected val creator = TriggerAudioResponseCreator(creatorFactory, writerFactory, responseAudioRepository)
    protected val updater = TriggerAudioResponseUpdater(responseAudioRepository, creatorFactory, writerFactory)

    protected fun `should return writer and creator by content`(content: TriggerAudioResponseContent) {
        every { creatorFactory.getInstance(content) } returns creatorDomainService.right()
        every { writerFactory.getInstance(content) } returns writer.right()
    }

    protected fun `should return audio by request`(audioResponse: TriggerAudioResponse, request: TriggerAudioResponseCreateRequest) {
        every { creatorDomainService.create(request.id, request.triggerId, request.guildId, request.content) } returns audioResponse.right()
    }

    protected fun `should write audio file`(audioResponse: TriggerAudioResponse, content: TriggerAudioResponseContent) {
        every { writer.write(audioResponse, content) } returns Unit.right()
    }
    
}
