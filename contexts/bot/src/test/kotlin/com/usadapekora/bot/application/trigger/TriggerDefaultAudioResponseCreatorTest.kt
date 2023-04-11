package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreator
import com.usadapekora.bot.domain.FileMother
import com.usadapekora.bot.domain.shared.file.DomainFileWriter
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.io.path.Path
import kotlin.random.Random
import kotlin.test.Test

class TriggerDefaultAudioResponseCreatorTest {

    @Test
    fun `should create trigger audio and save file`() {
        val repository = mockk<TriggerAudioDefaultRepository>(relaxed = true)
        val writer = mockk<DomainFileWriter>(relaxed = true)
        val creator = TriggerDefaultAudioResponseCreator(repository, writer)

        val id = UUID.randomUUID()
        val expected = TriggerAudioDefaultMother.create(id = id.toString(), file = "${id}.mp3")
        val file = Random.Default.nextBytes(10)

        every { repository.find(expected.id) } throws TriggerAudioResponseException.NotFound()

        creator.create(
            TriggerDefaultAudioResponseCreateRequest(
                id = expected.id.value,
                triggerId = expected.trigger.value,
                guildId = expected.guild.value,
                fileName = FileMother.filename(".mp3"),
                content = file
            )
        )

        verify { writer.write(file, Path(TriggerAudioUtils.audioDirPath(expected), "${expected.id.value}.mp3").toString()) }
        verify { repository.save(expected) }
    }

    @Test
    fun `should not create existing trigger audio`() {
        val repository = mockk<TriggerAudioDefaultRepository>(relaxed = true)
        val writer = mockk<DomainFileWriter>(relaxed = true)
        val creator = TriggerDefaultAudioResponseCreator(repository, writer)

        val expected = TriggerAudioDefaultMother.create()
        val file = Random.Default.nextBytes(10)

        every { repository.find(expected.id) } returns expected

        assertThrows<TriggerAudioResponseException.AlreadyExists> {
            creator.create(
                TriggerDefaultAudioResponseCreateRequest(
                    id = expected.id.value,
                    triggerId = expected.trigger.value,
                    guildId = expected.guild.value,
                    fileName = FileMother.filename(".mp3"),
                    content = file
                )
            )
        }
    }

}
