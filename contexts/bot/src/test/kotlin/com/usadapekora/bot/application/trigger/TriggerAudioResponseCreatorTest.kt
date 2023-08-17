package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreator
import com.usadapekora.bot.domain.FileMother
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseCreator as TriggerAudioResponseCreatorDomainService
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class TriggerAudioResponseCreatorTest {

    private val repository = mockk<TriggerAudioResponseRepository>(relaxed = true)
    private val writerFactory = mockk<TriggerAudioResponseWriterFactory>()
    private val creatorFactory = mockk<TriggerAudioResponseCreatorFactory>()
    private val creator = TriggerAudioResponseCreator(creatorFactory, writerFactory, repository)

    private val writer = mockk<TriggerAudioResponseWriter>()
    private val creatorDomainService = mockk<TriggerAudioResponseCreatorDomainService>()

    @BeforeTest
    fun resetMocks() = clearAllMocks()

    private fun `should return writer and creator by content`(content: TriggerAudioResponseContent) {
        every { creatorFactory.getInstance(content) } returns creatorDomainService.right()
        every { writerFactory.getInstance(content) } returns writer.right()
    }

    private fun `should return audio by request`(audioResponse: TriggerAudioResponse, request: TriggerAudioResponseCreateRequest) {
        every { creatorDomainService.create(request.id, request.triggerId, request.guildId, request.content) } returns audioResponse.right()
    }

    private fun `should write audio file`(audioResponse: TriggerAudioResponse, content: TriggerAudioResponseContent) {
        every { writer.write(audioResponse, content) } returns Unit.right()
    }

    @Test
    fun `it should save a new trigger audio`() {
        val trigger = TriggerMother.create()
        val file = Random.Default.nextBytes(10)
        val fileName = FileMother.filename(".mp3")
        val audioResponse = TriggerAudioResponseMother.create(
            source = "file",
            sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(trigger.guildId!!, trigger.id, fileName)
        )
        val request = TriggerAudioResponseCreateRequest(
            id = audioResponse.id.value,
            triggerId = trigger.id.value,
            guildId = trigger.guildId!!.value,
            content = TriggerAudioResponseFileContent(
                fileName = fileName,
                fileContent = file
            )
        )

        every { repository.find(audioResponse.id) } returns TriggerAudioResponseException.NotFound().left()
        `should return writer and creator by content`(request.content)
        `should return audio by request`(audioResponse, request)
        `should write audio file`(audioResponse, request.content)

        val result = creator.create(request)

        verify { repository.save(audioResponse) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

    @Test
    fun `it should not save a existing trigger audio`() {
        val trigger = TriggerMother.create()
        val file = Random.Default.nextBytes(10)
        val fileName = FileMother.filename(".mp3")
        val audioResponse = TriggerAudioResponseMother.create(
            source = "file",
            sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(trigger.guildId!!, trigger.id, fileName)
        )
        val request = TriggerAudioResponseCreateRequest(
            id = audioResponse.id.value,
            triggerId = trigger.id.value,
            guildId = trigger.guildId!!.value,
            content = TriggerAudioResponseFileContent(
                fileName = fileName,
                fileContent = file
            )
        )

        every { repository.find(audioResponse.id) } returns audioResponse.right()

        val result = creator.create(request)

        verify(inverse = true) { repository.save(audioResponse) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

}
