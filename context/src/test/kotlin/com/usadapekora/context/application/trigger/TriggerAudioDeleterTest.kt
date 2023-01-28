package com.usadapekora.context.application.trigger

import com.usadapekora.context.application.trigger.delete.TriggerAudioDeleter
import com.usadapekora.context.domain.TriggerAudioMother
import com.usadapekora.context.domain.shared.file.DomainFileDeleter
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
import com.usadapekora.context.domain.trigger.utils.TriggerAudioUtils
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
