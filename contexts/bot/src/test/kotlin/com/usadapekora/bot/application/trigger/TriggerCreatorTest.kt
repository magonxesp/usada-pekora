package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.create.TriggerCreateRequest
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.TriggerRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextResponseMother
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertTrue

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

        every { audioResponseRepository.find(audioResponse.id) } returns audioResponse.right()
        every { textResponseRepository.find(textResponse.id) } returns textResponse.right()
        every { repository.find(trigger.id) } returns TriggerException.NotFound().left()

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

        every { audioResponseRepository.find(audioResponse.id) } returns TriggerAudioResponseException.NotFound().left()
        every { textResponseRepository.find(textResponse.id) } returns TriggerTextResponseException.NotFound().left()
        every { repository.find(trigger.id) } returns TriggerException.NotFound().left()

        val result = creator.create(TriggerCreateRequest(
            id = trigger.id.value,
            title = trigger.title.value,
            input = trigger.input.value,
            compare = trigger.compare.value,
            discordGuildId = trigger.discordGuildId.value,
            responseTextId = trigger.responseText?.value,
            responseAudioId = trigger.responseAudio?.value,
            responseAudioProvider = trigger.responseAudioProvider?.value
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingResponse)

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

        every { audioResponseRepository.find(audioResponse.id) } returns audioResponse.right()
        every { textResponseRepository.find(textResponse.id) } returns textResponse.right()
        every { repository.find(trigger.id) } returns trigger.right()

        val result = creator.create(TriggerCreateRequest(
            id = trigger.id.value,
            title = trigger.title.value,
            input = trigger.input.value,
            compare = trigger.compare.value,
            discordGuildId = trigger.discordGuildId.value,
            responseTextId = trigger.responseText?.value,
            responseAudioId = trigger.responseAudio?.value,
            responseAudioProvider = trigger.responseAudioProvider?.value,
        ))

        assertTrue(result.leftOrNull() is TriggerException.AlreadyExists)
    }

    @Test
    fun `should not create and save a trigger`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val audioResponseRepository = mockk<TriggerAudioResponseRepository>()
        val textResponseRepository = mockk<TriggerTextResponseRepository>()
        val creator = TriggerCreator(repository, audioResponseRepository, textResponseRepository)

        every { repository.find(trigger.id) } returns TriggerException.NotFound().left()
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

        every { textResponseRepository.find(trigger.responseText!!) } returns TriggerTextResponseException.NotFound().left()
        every { repository.find(trigger.id) } returns TriggerException.NotFound().left()

        val result = creator.create(TriggerCreateRequest(
            id = trigger.id.value,
            title = trigger.title.value,
            input = trigger.input.value,
            compare = trigger.compare.value,
            discordGuildId = trigger.discordGuildId.value,
            responseTextId = trigger.responseText?.value,
            responseAudioId = null,
            responseAudioProvider = trigger.responseAudioProvider?.value,
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingResponse)

        verify(inverse = true) { repository.save(trigger) }
    }

    @Test
    fun `should not create and save a trigger with audio response that not exists`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val audioResponseRepository = mockk<TriggerAudioResponseRepository>()
        val textResponseRepository = mockk<TriggerTextResponseRepository>()
        val creator = TriggerCreator(repository, audioResponseRepository, textResponseRepository)

        every { audioResponseRepository.find(trigger.responseAudio!!) } returns TriggerAudioResponseException.NotFound().left()
        every { repository.find(trigger.id) } returns TriggerException.NotFound().left()

        val result = creator.create(TriggerCreateRequest(
            id = trigger.id.value,
            title = trigger.title.value,
            input = trigger.input.value,
            compare = trigger.compare.value,
            discordGuildId = trigger.discordGuildId.value,
            responseTextId = null,
            responseAudioId = trigger.responseAudio?.value,
            responseAudioProvider = trigger.responseAudioProvider?.value,
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingResponse)

        verify(inverse = true) { repository.save(trigger) }
    }

}
