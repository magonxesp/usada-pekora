package com.usadapekora.context.application.trigger

import com.usadapekora.context.domain.shared.file.UploadedFileWriter
import com.usadapekora.context.domain.TriggerAudioMother
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class TriggerAudioCreatorTest {

    @Test
    fun `should create trigger audio and save file`() {
        val repository = mockk<TriggerAudioRepository>(relaxed = true)
        val writer = mockk<UploadedFileWriter>(relaxed = true)
        val creator = TriggerAudioCreator(repository, writer)

        val expected = TriggerAudioMother.create()
        val file = arrayOf(Byte.MIN_VALUE)

        creator.create(TriggerAudioCreateRequest(
            id = expected.id.value,
            triggerId = expected.trigger.value,
            guildId = expected.guild.value,
            fileName = expected.name(),
            fileContent = file
        ))

        verify { repository.save(expected) }
        verify { writer.write(file, expected.path()) }
    }

}
