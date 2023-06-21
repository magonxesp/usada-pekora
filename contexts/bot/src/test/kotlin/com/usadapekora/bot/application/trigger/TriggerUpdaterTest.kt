package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.update.TriggerUpdateRequest
import com.usadapekora.bot.application.trigger.update.TriggerUpdater
import com.usadapekora.bot.domain.trigger.Trigger
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
import kotlin.test.Test
import kotlin.test.assertTrue

class TriggerUpdaterTest {

    @Test
    fun `should update a trigger`() {
        val trigger = TriggerMother.create()
        val responseText = TriggerTextResponseMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val textRepository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val audioRepository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val updater = TriggerUpdater(repository, textRepository, audioRepository)

        every { repository.find(trigger.id) } returns trigger.right()
        every { textRepository.find(responseText.id) } returns responseText.right()
        every { audioRepository.find(trigger.responseAudio!!) } returns TriggerAudioResponseException.NotFound().left()

        trigger.input = Trigger.TriggerInput("New expected user input")

        val result = updater.update(TriggerUpdateRequest(
            id = trigger.id.value,
            values = TriggerUpdateRequest.NewValues(
                input = "New expected user input",
                responseTextId = responseText.id.value,
            )
        ))

        assertTrue(result.isRight())

        verify { repository.save(trigger) }
    }

    @Test
    fun `should update not update a trigger that deletes all responses`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val textRepository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val audioRepository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val updater = TriggerUpdater(repository, textRepository, audioRepository)

        every { repository.find(trigger.id) } returns trigger.right()

        trigger.input = Trigger.TriggerInput("New expected user input")

        val result = updater.update(TriggerUpdateRequest(
            id = trigger.id.value,
            values = TriggerUpdateRequest.NewValues(
                input = "New expected user input",
            )
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingResponse)

        verify(inverse = true) { repository.save(trigger) }
    }

    @Test
    fun `should update the text response of a trigger`() {
        val trigger = TriggerMother.create()
        val responseText = TriggerTextResponseMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val textRepository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val audioRepository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val updater = TriggerUpdater(repository, textRepository, audioRepository)

        every { repository.find(trigger.id) } returns trigger.right()
        every { textRepository.find(responseText.id) } returns responseText.right()

        trigger.responseText = responseText.id // updated text response

        val result = updater.update(TriggerUpdateRequest(
            id = trigger.id.value,
            values = TriggerUpdateRequest.NewValues(
                responseTextId = responseText.id.value,
            )
        ))

        assertTrue(result.isRight())

        verify { textRepository.find(responseText.id) }
        verify { repository.save(trigger) }
    }

    @Test
    fun `should not update not existing text response of a trigger`() {
        val trigger = TriggerMother.create()
        val responseText = TriggerTextResponseMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val textRepository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val audioRepository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val updater = TriggerUpdater(repository, textRepository, audioRepository)

        every { repository.find(trigger.id) } returns trigger.right()
        every { textRepository.find(responseText.id) } returns TriggerTextResponseException.NotFound().left()

        trigger.responseText = responseText.id // updated text response

        val result = updater.update(TriggerUpdateRequest(
            id = trigger.id.value,
            values = TriggerUpdateRequest.NewValues(
                responseTextId = responseText.id.value,
            )
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingResponse)

        verify { textRepository.find(responseText.id) }
        verify(inverse = true) { repository.save(trigger) }
    }

    @Test
    fun `should update the audio response of a trigger`() {
        val trigger = TriggerMother.create()
        val responseAudio = TriggerAudioDefaultMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val textRepository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val audioRepository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val updater = TriggerUpdater(repository, textRepository, audioRepository)

        every { repository.find(trigger.id) } returns trigger.right()
        every { audioRepository.find(responseAudio.id, responseAudio.provider) } returns responseAudio.right()

        trigger.responseAudio = responseAudio.id // updated audio response
        trigger.responseAudioProvider = responseAudio.provider // updated audio response

        val result = updater.update(TriggerUpdateRequest(
            id = trigger.id.value,
            values = TriggerUpdateRequest.NewValues(
                responseAudioId = responseAudio.id.value,
                responseAudioProvider = responseAudio.provider.value
            )
        ))

        assertTrue(result.isRight())

        verify { audioRepository.find(responseAudio.id) }
        verify { repository.save(trigger) }
    }

    @Test
    fun `should not update not existing audio response of a trigger`() {
        val trigger = TriggerMother.create()
        val responseAudio = TriggerAudioDefaultMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val textRepository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val audioRepository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val updater = TriggerUpdater(repository, textRepository, audioRepository)

        every { repository.find(trigger.id) } returns trigger.right()
        every { audioRepository.find(responseAudio.id, responseAudio.provider) } returns TriggerAudioResponseException.NotFound().left()

        trigger.responseAudio = responseAudio.id // updated audio response
        trigger.responseAudioProvider = responseAudio.provider // updated audio response

        val result = updater.update(TriggerUpdateRequest(
            id = trigger.id.value,
            values = TriggerUpdateRequest.NewValues(
                responseAudioId = responseAudio.id.value,
                responseAudioProvider = responseAudio.provider.value
            )
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingResponse)

        verify { audioRepository.find(responseAudio.id) }
        verify(inverse = true) { repository.save(trigger) }
    }

    @Test
    fun `should not update audio response without audio provider of a trigger`() {
        val trigger = TriggerMother.create()
        val responseAudio = TriggerAudioDefaultMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val textRepository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val audioRepository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val updater = TriggerUpdater(repository, textRepository, audioRepository)

        every { repository.find(trigger.id) } returns trigger.right()
        every { audioRepository.find(responseAudio.id, responseAudio.provider) } returns responseAudio.right()

        trigger.responseAudio = responseAudio.id // updated audio response
        trigger.responseAudioProvider = responseAudio.provider // updated audio response

        val result = updater.update(TriggerUpdateRequest(
            id = trigger.id.value,
            values = TriggerUpdateRequest.NewValues(
                responseAudioId = responseAudio.id.value,
            )
        ))

        assertTrue(result.leftOrNull() is TriggerException.MissingAudioProvider)

        verify(inverse = true) { repository.save(trigger) }
    }

}
