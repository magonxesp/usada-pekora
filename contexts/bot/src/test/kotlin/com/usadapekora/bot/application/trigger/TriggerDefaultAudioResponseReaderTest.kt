package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.read.TriggerDefaultAudioReader
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.shared.file.DomainFileReader
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.io.path.Path
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerDefaultAudioResponseReaderTest {

    @Test
    fun `should read the trigger audio file by id`() {
        val fileReader = mockk<DomainFileReader>()
        val repository = mockk<TriggerAudioDefaultRepository>()
        val reader = TriggerDefaultAudioReader(repository, fileReader)
        val audio = TriggerAudioDefaultMother.create()
        val expectedContent = Random.nextBytes(10)

        every { repository.find(audio.id) } returns audio
        every { fileReader.read(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString()) } returns expectedContent

        val content = reader.read(audio.id.value)

        assertEquals(content, expectedContent)
    }

    @Test
    fun `should not read the trigger audio if it not exists`() {
        val fileReader = mockk<DomainFileReader>()
        val repository = mockk<TriggerAudioDefaultRepository>()
        val reader = TriggerDefaultAudioReader(repository, fileReader)
        val audio = TriggerAudioDefaultMother.create()

        every { repository.find(audio.id) } throws TriggerAudioResponseException.NotFound()

        assertThrows<TriggerAudioResponseException.NotFound> {
            reader.read(audio.id.value)
        }

        verify(inverse = true) { fileReader.read(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString()) }
    }

}
