package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.application.trigger.delete.audio.TriggerDefaultAudioDeleter
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.io.path.Path
import kotlin.test.Test

class TriggerDefaultAudioResponseDeleterTest {

    @Test
    fun `should delete an trigger audio and its content`() {
        val repository = mockk<TriggerAudioDefaultRepository>(relaxed = true)
        val fileDeleter = mockk<DomainFileDeleter>(relaxed = true)
        val deleter = TriggerDefaultAudioDeleter(repository, fileDeleter)
        val audio = TriggerAudioDefaultMother.create()

        every { repository.find(audio.id) } returns audio.right()

        deleter.delete(audio.id.value)

        verify { fileDeleter.delete(audio.path) }
        verify { repository.delete(audio) }
    }

}
