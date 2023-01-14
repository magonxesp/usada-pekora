package com.usadapekora.context.application.trigger

import com.usadapekora.context.application.trigger.create.TriggerAudioCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerAudioCreator
import com.usadapekora.context.domain.FileMother
import com.usadapekora.context.domain.shared.file.DomainFileWriter
import com.usadapekora.context.domain.TriggerAudioMother
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

class TriggerAudioCreatorTest {

    @Test
    fun `should create trigger audio and save file`() {
        val repository = mockk<TriggerAudioRepository>(relaxed = true)
        val writer = mockk<DomainFileWriter>(relaxed = true)
        val creator = TriggerAudioCreator(repository, writer)

        val expected = TriggerAudioMother.create()
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
