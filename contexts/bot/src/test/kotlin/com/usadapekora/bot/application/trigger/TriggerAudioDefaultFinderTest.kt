package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.find.TriggerAudioFinder
import com.usadapekora.bot.application.trigger.find.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.exception.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerAudioDefaultFinderTest {

    @Test
    fun `should find trigger audio by id`() {
        val repository = mockk<TriggerAudioDefaultRepository>()
        val finder = TriggerAudioFinder(repository)
        val triggerAudio = TriggerAudioDefaultMother.create()

        every { repository.find(triggerAudio.id) } returns triggerAudio

        val actual = finder.find(triggerAudio.id.value)
        assertEquals(TriggerAudioResponse.fromEntity(triggerAudio), actual)
    }

    @Test
    fun `should not find trigger audio by id`() {
        val repository = mockk<TriggerAudioDefaultRepository>()
        val finder = TriggerAudioFinder(repository)
        val triggerAudio = TriggerAudioDefaultMother.create()

        every { repository.find(triggerAudio.id) } throws TriggerAudioResponseException.NotFound()

        assertThrows<TriggerAudioResponseException.NotFound> {
            finder.find(triggerAudio.id.value)
        }
    }

    @Test
    fun `should find trigger audio by trigger id`() {
        val repository = mockk<TriggerAudioDefaultRepository>()
        val finder = TriggerAudioFinder(repository)
        val triggerAudio = TriggerAudioDefaultMother.create()

        every { repository.findByTrigger(triggerAudio.trigger) } returns triggerAudio

        val actual = finder.findByTriggerId(triggerAudio.trigger.value)
        assertEquals(TriggerAudioResponse.fromEntity(triggerAudio), actual)
    }

    @Test
    fun `should not find trigger audio by trigger id`() {
        val repository = mockk<TriggerAudioDefaultRepository>()
        val finder = TriggerAudioFinder(repository)
        val triggerAudio = TriggerAudioDefaultMother.create()

        every { repository.findByTrigger(triggerAudio.trigger) } throws TriggerAudioResponseException.NotFound()

        assertThrows<TriggerAudioResponseException.NotFound> {
            finder.findByTriggerId(triggerAudio.trigger.value)
        }
    }

}
