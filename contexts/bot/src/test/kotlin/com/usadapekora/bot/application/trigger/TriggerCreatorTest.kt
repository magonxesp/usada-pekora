package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.create.TriggerCreateRequest
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerKind
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.TriggerRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseMother
import com.usadapekora.bot.domain.trigger.TriggerTextResponseMother
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
    fun `it should create and save a trigger`() {
        val audioResponse = TriggerAudioResponseMother.create()
        val textResponse = TriggerTextResponseMother.create()
        val trigger = TriggerMother.create(kind = TriggerKind.PRIVATE.value, responseTextId = textResponse.id.value, responseAudioId = audioResponse.id.value)

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
            guildId = trigger.guildId!!.value
        ))

        verify { audioResponseRepository.find(audioResponse.id) }
        verify { textResponseRepository.find(textResponse.id) }
        verify { repository.save(trigger) }
    }

    @Test
    fun `it should not create and save a trigger without response`() {
        val audioResponse = TriggerAudioResponseMother.create()
        val textResponse = TriggerTextResponseMother.create()
        val trigger = TriggerMother.create(kind = TriggerKind.PRIVATE.value, responseTextId = textResponse.id.value, responseAudioId = audioResponse.id.value)

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
            guildId = trigger.guildId!!.value,
            responseTextId = trigger.responseText?.value,
            responseAudioId = trigger.responseAudio?.value,
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingResponse)

        verify { audioResponseRepository.find(audioResponse.id) }
        verify { textResponseRepository.find(textResponse.id) }
        verify(inverse = true) { repository.save(trigger) }
    }

    @Test
    fun `it should not create an existing trigger`() {
        val audioResponse = TriggerAudioResponseMother.create()
        val textResponse = TriggerTextResponseMother.create()
        val trigger = TriggerMother.create(kind = TriggerKind.PRIVATE.value, responseTextId = textResponse.id.value, responseAudioId = audioResponse.id.value)
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
            guildId = trigger.guildId!!.value,
            responseTextId = trigger.responseText?.value,
            responseAudioId = trigger.responseAudio?.value,
        ))

        assertTrue(result.leftOrNull() is TriggerException.AlreadyExists)
    }

    @Test
    fun `it should not create and save a trigger`() {
        val trigger = TriggerMother.create(kind = TriggerKind.PRIVATE.value)
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
                guildId = trigger.guildId!!.value,
                responseTextId = trigger.responseText?.value,
                responseAudioId = trigger.responseAudio?.value,
            ))
        }
    }

    @Test
    fun `it should not create and save a trigger with text response that not exists`() {
        val trigger = TriggerMother.create(kind = TriggerKind.PRIVATE.value)
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
            guildId = trigger.guildId!!.value,
            responseTextId = trigger.responseText?.value,
            responseAudioId = null,
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingResponse)

        verify(inverse = true) { repository.save(trigger) }
    }

    @Test
    fun `it should not create and save a trigger with audio response that not exists`() {
        val trigger = TriggerMother.create(kind = TriggerKind.PRIVATE.value)
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
            guildId = trigger.guildId!!.value,
            responseTextId = null,
            responseAudioId = trigger.responseAudio?.value,
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingResponse)

        verify(inverse = true) { repository.save(trigger) }
    }

}
