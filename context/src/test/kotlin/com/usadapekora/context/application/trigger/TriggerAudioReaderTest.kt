package com.usadapekora.context.application.trigger

import com.usadapekora.context.application.trigger.read.TriggerAudioReader
import com.usadapekora.context.domain.TriggerAudioMother
import com.usadapekora.context.domain.shared.file.DomainFileReader
import com.usadapekora.context.domain.trigger.TriggerAudioException
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
import com.usadapekora.context.domain.trigger.utils.TriggerAudioUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.io.path.Path
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerAudioReaderTest {

    @Test
    fun `should read the trigger audio file by id`() {
        val fileReader = mockk<DomainFileReader>()
        val repository = mockk<TriggerAudioRepository>()
        val reader = TriggerAudioReader(repository, fileReader)
        val audio = TriggerAudioMother.create()
        val expectedContent = Random.nextBytes(10)

        every { repository.find(audio.id) } returns audio
        every { fileReader.read(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString()) } returns expectedContent

        val content = reader.read(audio.id.value)

        assertEquals(content, expectedContent)
    }

    @Test
    fun `should not read the trigger audio if it not exists`() {
        val fileReader = mockk<DomainFileReader>()
        val repository = mockk<TriggerAudioRepository>()
        val reader = TriggerAudioReader(repository, fileReader)
        val audio = TriggerAudioMother.create()

        every { repository.find(audio.id) } throws TriggerAudioException.NotFound()

        assertThrows<TriggerAudioException.NotFound> {
            reader.read(audio.id.value)
        }

        verify(inverse = true) { fileReader.read(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString()) }
    }

}
