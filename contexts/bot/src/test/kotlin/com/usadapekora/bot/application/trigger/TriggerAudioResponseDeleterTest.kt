package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.TriggerModuleUnitTestCase
import com.usadapekora.bot.application.trigger.delete.audio.TriggerAudioResponseDeleter
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.shared.domain.file.DomainFileDeleter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertTrue

class TriggerAudioResponseDeleterTest : TriggerModuleUnitTestCase() {

    @Test
    fun `should delete an trigger audio and its content`() {
        val audio = TriggerAudioResponseMother.create(kind = TriggerAudioResponse.TriggerAudioResponseKind.FILE.name)

        every { responseAudioRepository.find(audio.id) } returns audio.right()
        every { domainFileDeleter.delete(audio.source.value) } returns Unit.right()

        val result = deleter.delete(audio.id.value)

        assertTrue(result.isRight())

        verify { domainFileDeleter.delete(audio.source.value) }
        verify { responseAudioRepository.delete(audio) }
    }

    @Test
    fun `should delete an trigger audio without file`() {
        val audio = TriggerAudioResponseMother.create(kind = TriggerAudioResponse.TriggerAudioResponseKind.RESOURCE.name)

        every { responseAudioRepository.find(audio.id) } returns audio.right()
        every { domainFileDeleter.delete(any()) } returns Unit.right()

        val result = deleter.delete(audio.id.value)

        assertTrue(result.isRight())

        verify(inverse = true) { domainFileDeleter.delete(audio.source.value) }
        verify { responseAudioRepository.delete(audio) }
    }

}
