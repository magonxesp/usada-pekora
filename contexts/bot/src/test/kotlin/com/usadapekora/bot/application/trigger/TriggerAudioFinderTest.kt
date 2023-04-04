package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.find.TriggerAudioFinder
import com.usadapekora.bot.application.trigger.find.TriggerAudioResponse
import com.usadapekora.bot.domain.TriggerAudioMother
import com.usadapekora.bot.domain.trigger.TriggerAudioException
import com.usadapekora.bot.domain.trigger.TriggerAudioRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerAudioFinderTest {

    @Test
    fun `should find trigger audio by id`() {
        val repository = mockk<TriggerAudioRepository>()
        val finder = TriggerAudioFinder(repository)
        val triggerAudio = TriggerAudioMother.create()

        every { repository.find(triggerAudio.id) } returns triggerAudio

        val actual = finder.find(triggerAudio.id.value)
        assertEquals(TriggerAudioResponse.fromEntity(triggerAudio), actual)
    }

    @Test
    fun `should not find trigger audio by id`() {
        val repository = mockk<TriggerAudioRepository>()
        val finder = TriggerAudioFinder(repository)
        val triggerAudio = TriggerAudioMother.create()

        every { repository.find(triggerAudio.id) } throws TriggerAudioException.NotFound()

        assertThrows<TriggerAudioException.NotFound> {
            finder.find(triggerAudio.id.value)
        }
    }

    @Test
    fun `should find trigger audio by trigger id`() {
        val repository = mockk<TriggerAudioRepository>()
        val finder = TriggerAudioFinder(repository)
        val triggerAudio = TriggerAudioMother.create()

        every { repository.findByTrigger(triggerAudio.trigger) } returns triggerAudio

        val actual = finder.findByTriggerId(triggerAudio.trigger.value)
        assertEquals(TriggerAudioResponse.fromEntity(triggerAudio), actual)
    }

    @Test
    fun `should not find trigger audio by trigger id`() {
        val repository = mockk<TriggerAudioRepository>()
        val finder = TriggerAudioFinder(repository)
        val triggerAudio = TriggerAudioMother.create()

        every { repository.findByTrigger(triggerAudio.trigger) } throws TriggerAudioException.NotFound()

        assertThrows<TriggerAudioException.NotFound> {
            finder.findByTriggerId(triggerAudio.trigger.value)
        }
    }

}
