package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.create.TriggerAudioCreateRequest
import com.usadapekora.bot.application.trigger.create.TriggerAudioCreator
import com.usadapekora.bot.domain.FileMother
import com.usadapekora.bot.domain.shared.file.DomainFileWriter
import com.usadapekora.bot.domain.TriggerAudioMother
import com.usadapekora.bot.domain.trigger.TriggerAudioException
import com.usadapekora.bot.domain.trigger.TriggerAudioRepository
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.io.path.Path
import kotlin.random.Random
import kotlin.test.Test

class TriggerAudioCreatorTest {

    @Test
    fun `should create trigger audio and save file`() {
        val repository = mockk<TriggerAudioRepository>(relaxed = true)
        val writer = mockk<DomainFileWriter>(relaxed = true)
        val creator = TriggerAudioCreator(repository, writer)

        val id = UUID.randomUUID()
        val expected = TriggerAudioMother.create(id = id.toString(), file = "${id}.mp3")
        val file = Random.Default.nextBytes(10)

        every { repository.find(expected.id) } throws TriggerAudioException.NotFound()

        creator.create(
            TriggerAudioCreateRequest(
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
        val repository = mockk<TriggerAudioRepository>(relaxed = true)
        val writer = mockk<DomainFileWriter>(relaxed = true)
        val creator = TriggerAudioCreator(repository, writer)

        val expected = TriggerAudioMother.create()
        val file = Random.Default.nextBytes(10)

        every { repository.find(expected.id) } returns expected

        assertThrows<TriggerAudioException.AlreadyExists> {
            creator.create(
                TriggerAudioCreateRequest(
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
