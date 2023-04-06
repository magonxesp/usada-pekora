package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.create.TriggerCreateRequest
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.domain.trigger.exception.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.exception.TriggerException
import com.usadapekora.bot.domain.trigger.exception.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextMother
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class TriggerCreatorTest {

    @Test
    fun `should create and save a trigger`() {
        val audioResponse = TriggerAudioDefaultMother.create()
        val textResponse = TriggerTextMother.create()
        val trigger = TriggerMother.create(responseText = textResponse, responseAudio = audioResponse)

        val repository = mockk<TriggerRepository>(relaxed = true)
        val audioResponseRepository = mockk<TriggerAudioResponseRepository>()
        val textResponseRepository = mockk<TriggerTextResponseRepository>()
        val creator = TriggerCreator(repository, audioResponseRepository, textResponseRepository)

        every { audioResponseRepository.find(audioResponse.id) } returns audioResponse
        every { textResponseRepository.find(textResponse.id) } returns textResponse
        every { repository.find(trigger.id) } throws TriggerException.NotFound()

        creator.create(TriggerCreateRequest(
            id = trigger.id.value,
            title = trigger.title.value,
            input = trigger.input.value,
            compare = trigger.compare.value,
            responseTextId = trigger.responseText?.id(),
            responseAudioId = trigger.responseAudio?.id(),
            responseAudioProvider = trigger.responseAudio?.provider()?.value,
            discordGuildId = trigger.discordGuildId.value
        ))

        verify { audioResponseRepository.find(audioResponse.id) }
        verify { textResponseRepository.find(textResponse.id) }
        verify { repository.save(trigger) }
    }

    @Test
    fun `should not create and save a trigger without response`() {
        val audioResponse = TriggerAudioDefaultMother.create()
        val textResponse = TriggerTextMother.create()
        val trigger = TriggerMother.create(responseText = textResponse, responseAudio = audioResponse)

        val repository = mockk<TriggerRepository>(relaxed = true)
        val audioResponseRepository = mockk<TriggerAudioResponseRepository>()
        val textResponseRepository = mockk<TriggerTextResponseRepository>()
        val creator = TriggerCreator(repository, audioResponseRepository, textResponseRepository)

        every { audioResponseRepository.find(audioResponse.id) } throws TriggerAudioResponseException.NotFound()
        every { textResponseRepository.find(textResponse.id) } throws TriggerTextResponseException.NotFound()
        every { repository.find(trigger.id) } throws TriggerException.NotFound()

        assertThrows<TriggerException.MissingResponse> {
            creator.create(TriggerCreateRequest(
                id = trigger.id.value,
                title = trigger.title.value,
                input = trigger.input.value,
                compare = trigger.compare.value,
                discordGuildId = trigger.discordGuildId.value,
                responseTextId = trigger.responseText?.id(),
                responseAudioId = trigger.responseAudio?.id(),
                responseAudioProvider = trigger.responseAudio?.provider()?.value
            ))
        }

        verify { audioResponseRepository.find(audioResponse.id) }
        verify { textResponseRepository.find(textResponse.id) }
        verify(inverse = true) { repository.save(trigger) }
    }

    @Test
    fun `should not create an existing trigger`() {
        val audioResponse = TriggerAudioDefaultMother.create()
        val textResponse = TriggerTextMother.create()
        val trigger = TriggerMother.create(responseText = textResponse, responseAudio = audioResponse)
        val repository = mockk<TriggerRepository>(relaxed = true)
        val audioResponseRepository = mockk<TriggerAudioResponseRepository>()
        val textResponseRepository = mockk<TriggerTextResponseRepository>()
        val creator = TriggerCreator(repository, audioResponseRepository, textResponseRepository)

        every { audioResponseRepository.find(audioResponse.id) } returns audioResponse
        every { textResponseRepository.find(textResponse.id) } returns textResponse
        every { repository.find(trigger.id) } returns trigger

        assertThrows<TriggerException.AlreadyExists> {
            creator.create(TriggerCreateRequest(
                id = trigger.id.value,
                title = trigger.title.value,
                input = trigger.input.value,
                compare = trigger.compare.value,
                discordGuildId = trigger.discordGuildId.value,
                responseTextId = trigger.responseText?.id(),
                responseAudioId = trigger.responseAudio?.id(),
                responseAudioProvider = trigger.responseAudio?.provider()?.value,
            ))
        }
    }

    @Test
    fun `should not create and save a trigger`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val audioResponseRepository = mockk<TriggerAudioResponseRepository>()
        val textResponseRepository = mockk<TriggerTextResponseRepository>()
        val creator = TriggerCreator(repository, audioResponseRepository, textResponseRepository)

        every { repository.find(trigger.id) } throws TriggerException.NotFound()
        every { repository.save(trigger) } throws Exception()

        assertThrows<Exception> {
            creator.create(TriggerCreateRequest(
                id = trigger.id.value,
                title = trigger.title.value,
                input = trigger.input.value,
                compare = trigger.compare.value,
                discordGuildId = trigger.discordGuildId.value,
                responseTextId = trigger.responseText?.id(),
                responseAudioId = trigger.responseAudio?.id(),
                responseAudioProvider = trigger.responseAudio?.provider()?.value,
            ))
        }
    }

}
