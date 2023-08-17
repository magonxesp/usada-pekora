package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.read.TriggerDefaultAudioReader
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseSourceUriFactory
import com.usadapekora.shared.domain.file.DomainFileReader
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TriggerAudioResponseReaderTest {

    @Test
    fun `should read the trigger audio file by id`() {
        val fileReader = mockk<DomainFileReader>()
        val repository = mockk<TriggerAudioResponseRepository>()
        val reader = TriggerDefaultAudioReader(repository, fileReader)
        val audio = TriggerAudioResponseMother.create()
        val expectedContent = Random.nextBytes(10)
        val audioPath = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(audio.sourceUri.value).getOrNull()!!

        every { repository.find(audio.id) } returns audio.right()
        every { fileReader.read(audioPath) } returns expectedContent.right()

        val content = reader.read(audio.id.value).getOrNull()

        assertEquals(content, expectedContent)
    }

    @Test
    fun `should not read the trigger audio if it not exists`() {
        val fileReader = mockk<DomainFileReader>()
        val repository = mockk<TriggerAudioResponseRepository>()
        val reader = TriggerDefaultAudioReader(repository, fileReader)
        val audio = TriggerAudioResponseMother.create()
        val audioPath = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(audio.sourceUri.value).getOrNull()!!

        every { repository.find(audio.id) } returns TriggerAudioResponseException.NotFound().left()

        val result = reader.read(audio.id.value)

        assertTrue(result.leftOrNull() is TriggerAudioResponseException.NotFound)
        verify(inverse = true) { fileReader.read(audioPath) }
    }

}
