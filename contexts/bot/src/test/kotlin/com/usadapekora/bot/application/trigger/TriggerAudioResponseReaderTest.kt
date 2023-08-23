package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.read.TriggerAudioResponseReader
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.shared.domain.file.DomainFileReader
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TriggerAudioResponseReaderTest {

    @Test
    fun `should read the trigger audio file by id`() {
        val fileReader = mockk<DomainFileReader>()
        val repository = mockk<TriggerAudioResponseRepository>()
        val reader = TriggerAudioResponseReader(repository, fileReader)
        val audio = TriggerAudioResponseMother.create(kind = TriggerAudioResponse.TriggerAudioResponseKind.FILE.name)
        val expectedContent = Random.nextBytes(10)

        every { repository.find(audio.id) } returns audio.right()
        every { fileReader.read(audio.source.value) } returns expectedContent.right()

        val result = reader.read(audio.id.value)

        assertEquals(expectedContent, result.getOrNull(), result.leftOrNull()?.message)
    }

    @Test
    fun `should not read the trigger audio if it not exists`() {
        val fileReader = mockk<DomainFileReader>()
        val repository = mockk<TriggerAudioResponseRepository>()
        val reader = TriggerAudioResponseReader(repository, fileReader)
        val audio = TriggerAudioResponseMother.create(kind = TriggerAudioResponse.TriggerAudioResponseKind.FILE.name)

        every { repository.find(audio.id) } returns TriggerAudioResponseException.NotFound().left()

        val result = reader.read(audio.id.value)

        assertIs<TriggerAudioResponseException.NotFound>(result.leftOrNull())
        verify(inverse = true) { fileReader.read(audio.source.value) }
    }

}
