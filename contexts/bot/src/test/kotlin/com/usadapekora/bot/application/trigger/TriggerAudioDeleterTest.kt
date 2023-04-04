package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.delete.TriggerAudioDeleter
import com.usadapekora.bot.domain.TriggerAudioMother
import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import com.usadapekora.bot.domain.trigger.TriggerAudioRepository
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.io.path.Path
import kotlin.test.Test

class TriggerAudioDeleterTest {

    @Test
    fun `should delete an trigger audio and its content`() {
        val repository = mockk<TriggerAudioRepository>(relaxed = true)
        val fileDeleter = mockk<DomainFileDeleter>(relaxed = true)
        val deleter = TriggerAudioDeleter(repository, fileDeleter)
        val audio = TriggerAudioMother.create()

        every { repository.find(audio.id) } returns audio

        deleter.delete(audio.id.value)

        verify { fileDeleter.delete(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString()) }
        verify { repository.delete(audio) }
    }

}
