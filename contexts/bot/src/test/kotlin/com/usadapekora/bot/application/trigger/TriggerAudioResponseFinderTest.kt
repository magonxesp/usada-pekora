package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.application.trigger.find.audio.TriggerAudioResponseFindResponse
import com.usadapekora.bot.application.trigger.find.audio.TriggerAudioResponseFinder
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerAudioResponseFinderTest {

    @Test
    fun `should find trigger audio by id`() {
        val repository = mockk<TriggerAudioResponseRepository>()
        val finder = TriggerAudioResponseFinder(repository)
        val triggerAudio = TriggerAudioResponseMother.create()

        every { repository.find(triggerAudio.id) } returns triggerAudio.right()

        val actual = finder.find(triggerAudio.id.value).getOrNull()
        assertEquals(TriggerAudioResponseFindResponse.fromEntity(triggerAudio), actual)
    }

    @Test
    fun `should not find trigger audio by id`() {
        val repository = mockk<TriggerAudioResponseRepository>()
        val finder = TriggerAudioResponseFinder(repository)
        val triggerAudio = TriggerAudioResponseMother.create()

        every { repository.find(triggerAudio.id) } throws TriggerAudioResponseException.NotFound()

        assertThrows<TriggerAudioResponseException.NotFound> {
            finder.find(triggerAudio.id.value)
        }
    }

    @Test
    fun `should find trigger audio by trigger id`() {
        val repository = mockk<TriggerAudioResponseRepository>()
        val finder = TriggerAudioResponseFinder(repository)
        val trigger = TriggerMother.create()
        val triggerAudio = TriggerAudioResponseMother.create()

        every { repository.findByTrigger(trigger.id) } returns triggerAudio.right()

        val actual = finder.findByTriggerId(trigger.id.value).getOrNull()
        assertEquals(TriggerAudioResponseFindResponse.fromEntity(triggerAudio), actual)
    }

    @Test
    fun `should not find trigger audio by trigger id`() {
        val repository = mockk<TriggerAudioResponseRepository>()
        val finder = TriggerAudioResponseFinder(repository)
        val trigger = TriggerMother.create()

        every { repository.findByTrigger(trigger.id) } throws TriggerAudioResponseException.NotFound()

        assertThrows<TriggerAudioResponseException.NotFound> {
            finder.findByTriggerId(trigger.id.value)
        }
    }

}
