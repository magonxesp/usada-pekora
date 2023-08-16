package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreator
import com.usadapekora.bot.domain.FileMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import com.usadapekora.shared.domain.file.DomainFileWriter
import com.usadapekora.shared.storageDirPath
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.io.File
import java.util.*
import kotlin.io.path.Path
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class TriggerAudioResponseCreatorTest {

    @Test
    fun `should create trigger audio and save file`() {
        val repository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val writer = mockk<DomainFileWriter>(relaxed = true)
        val creator = TriggerAudioResponseCreator(repository, writer)

        val id = UUID.randomUUID()
        val filename = FileMother.filename(".mp3")
        val expected = TriggerAudioResponseMother.create(id = id.toString(), file = filename)
        val file = Random.Default.nextBytes(10)
        val destinationPath = Path(storageDirPath, "trigger", "audio", expected.guild.value, expected.trigger.value, "${expected.id.value}.${File(expected.sourceUri.value).extension}").toString()

        every { repository.find(expected.id) } returns TriggerAudioResponseException.NotFound().left()
        every { writer.write(file, destinationPath) } returns Unit.right()

        val result = creator.create(
            TriggerAudioResponseCreateRequest(
                id = expected.id.value,
                triggerId = expected.trigger.value,
                guildId = expected.guild.value,
                fileName = filename,
                content = file
            )
        )

        assertTrue(result.isRight())

        verify { writer.write(file, destinationPath) }
        verify { repository.save(expected) }
    }

    @Test
    fun `should not create existing trigger audio`() {
        val repository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val writer = mockk<DomainFileWriter>(relaxed = true)
        val creator = TriggerAudioResponseCreator(repository, writer)

        val expected = TriggerAudioResponseMother.create()
        val file = Random.Default.nextBytes(10)

        every { repository.find(expected.id) } returns expected.right()

        val result = creator.create(
            TriggerAudioResponseCreateRequest(
                id = expected.id.value,
                triggerId = expected.trigger.value,
                guildId = expected.guild.value,
                fileName = FileMother.filename(".mp3"),
                content = file
            )
        )

        assertTrue(result.leftOrNull() is TriggerAudioResponseException.AlreadyExists)
    }

}
