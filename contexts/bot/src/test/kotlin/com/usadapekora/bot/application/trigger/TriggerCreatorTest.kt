package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.create.TriggerCreateRequest
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextResponseMother
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class TriggerCreatorTest {

    @Test
    fun `should create and save a trigger`() {
        val audioResponse = TriggerAudioDefaultMother.create()
        val textResponse = TriggerTextResponseMother.create()
        val trigger = TriggerMother.create(responseTextId = textResponse.id.value, responseAudioId = audioResponse.id.value)

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
            responseTextId = trigger.responseText?.value,
            responseAudioId = trigger.responseAudio?.value,
            responseAudioProvider = trigger.responseAudioProvider?.value,
            discordGuildId = trigger.discordGuildId.value
        ))

        verify { audioResponseRepository.find(audioResponse.id) }
        verify { textResponseRepository.find(textResponse.id) }
        verify { repository.save(trigger) }
    }

    @Test
    fun `should not create and save a trigger without response`() {
        val audioResponse = TriggerAudioDefaultMother.create()
        val textResponse = TriggerTextResponseMother.create()
        val trigger = TriggerMother.create(responseTextId = textResponse.id.value, responseAudioId = audioResponse.id.value)

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
                responseTextId = trigger.responseText?.value,
                responseAudioId = trigger.responseAudio?.value,
                responseAudioProvider = trigger.responseAudioProvider?.value
            ))
        }

        verify { audioResponseRepository.find(audioResponse.id) }
        verify { textResponseRepository.find(textResponse.id) }
        verify(inverse = true) { repository.save(trigger) }
    }

    @Test
    fun `should not create an existing trigger`() {
        val audioResponse = TriggerAudioDefaultMother.create()
        val textResponse = TriggerTextResponseMother.create()
        val trigger = TriggerMother.create(responseTextId = textResponse.id.value, responseAudioId = audioResponse.id.value)
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
                responseTextId = trigger.responseText?.value,
                responseAudioId = trigger.responseAudio?.value,
                responseAudioProvider = trigger.responseAudioProvider?.value,
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
                responseTextId = trigger.responseText?.value,
                responseAudioId = trigger.responseAudio?.value,
                responseAudioProvider = trigger.responseAudioProvider?.value,
            ))
        }
    }

    @Test
    fun `should not create and save a trigger with text response that not exists`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val audioResponseRepository = mockk<TriggerAudioResponseRepository>()
        val textResponseRepository = mockk<TriggerTextResponseRepository>()
        val creator = TriggerCreator(repository, audioResponseRepository, textResponseRepository)

        every { textResponseRepository.find(trigger.responseText!!) } throws TriggerTextResponseException.NotFound()
        every { repository.find(trigger.id) } throws TriggerException.NotFound()

        assertThrows<TriggerException.MissingResponse> {
            creator.create(TriggerCreateRequest(
                id = trigger.id.value,
                title = trigger.title.value,
                input = trigger.input.value,
                compare = trigger.compare.value,
                discordGuildId = trigger.discordGuildId.value,
                responseTextId = trigger.responseText?.value,
                responseAudioId = null,
                responseAudioProvider = trigger.responseAudioProvider?.value,
            ))
        }

        verify(inverse = true) { repository.save(trigger) }
    }

    @Test
    fun `should not create and save a trigger with audio response that not exists`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val audioResponseRepository = mockk<TriggerAudioResponseRepository>()
        val textResponseRepository = mockk<TriggerTextResponseRepository>()
        val creator = TriggerCreator(repository, audioResponseRepository, textResponseRepository)

        every { audioResponseRepository.find(trigger.responseAudio!!) } throws TriggerAudioResponseException.NotFound()
        every { repository.find(trigger.id) } throws TriggerException.NotFound()

        assertThrows<TriggerException.MissingResponse> {
            creator.create(TriggerCreateRequest(
                id = trigger.id.value,
                title = trigger.title.value,
                input = trigger.input.value,
                compare = trigger.compare.value,
                discordGuildId = trigger.discordGuildId.value,
                responseTextId = null,
                responseAudioId = trigger.responseAudio?.value,
                responseAudioProvider = trigger.responseAudioProvider?.value,
            ))
        }

        verify(inverse = true) { repository.save(trigger) }
    }

}
