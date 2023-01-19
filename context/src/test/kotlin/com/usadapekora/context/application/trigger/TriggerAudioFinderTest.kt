package com.usadapekora.context.application.trigger

import com.usadapekora.context.application.trigger.find.TriggerAudioFinder
import com.usadapekora.context.application.trigger.find.TriggerAudioResponse
import com.usadapekora.context.domain.TriggerAudioMother
import com.usadapekora.context.domain.trigger.TriggerAudioException
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
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

}
