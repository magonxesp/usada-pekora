package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.application.trigger.delete.audio.TriggerAudioResponseDeleter
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import com.usadapekora.shared.domain.file.DomainFileDeleter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertTrue

class TriggerAudioResponseDeleterTest {

    @Test
    fun `should delete an trigger audio and its content`() {
        val repository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val fileDeleter = mockk<DomainFileDeleter>(relaxed = true)
        val deleter = TriggerAudioResponseDeleter(repository, fileDeleter)
        val audio = TriggerAudioResponseMother.create()

        every { repository.find(audio.id) } returns audio.right()
        every { fileDeleter.delete(audio.path) } returns Unit.right()

        val result = deleter.delete(audio.id.value)

        assertTrue(result.isRight())

        verify { fileDeleter.delete(audio.path) }
        verify { repository.delete(audio) }
    }

}
