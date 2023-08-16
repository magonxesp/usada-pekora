package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUpdater
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileWriter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class TriggerAudioResponseUpdaterTest {

    @Test
    fun `it should update a trigger default audio response`() {
        val newFileContent = Random.nextBytes(10)
        val audioResponse = TriggerAudioResponseMother.create()
        val newAudioResponse = TriggerAudioResponseMother.create(id = audioResponse.id.value)
        val repository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val fileWriter = mockk<DomainFileWriter>(relaxed = true)
        val fileDeleter = mockk<DomainFileDeleter>(relaxed = true)
        val updater = TriggerAudioResponseUpdater(repository, fileWriter, fileDeleter)

        every { repository.find(audioResponse.id) } returns audioResponse.right()
        every { fileDeleter.delete(audioResponse.path) } returns Unit.right()
        every { fileWriter.write(newFileContent, newAudioResponse.path) } returns Unit.right()

        val result = updater.update(
            TriggerAudioResponseUpdateRequest(
                id = audioResponse.id.value,
                values = TriggerAudioResponseUpdateRequest.NewValues(
                    triggerId = newAudioResponse.trigger.value,
                    guildId = newAudioResponse.guild.value,
                    fileName = newAudioResponse.sourceUri.value,
                    content = newFileContent
                )
            )
        )

        assertTrue(result.isRight())

        verify { fileWriter.write(newFileContent, newAudioResponse.path) }
        verify { repository.save(newAudioResponse) }
    }

    @Test
    fun `it should remove the old file when the path changes`() {
        val newFileContent = Random.nextBytes(10)
        val audioResponse = TriggerAudioResponseMother.create()
        val newAudioResponse = TriggerAudioResponseMother.create(id = audioResponse.id.value)
        val repository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val fileWriter = mockk<DomainFileWriter>(relaxed = true)
        val fileDeleter = mockk<DomainFileDeleter>(relaxed = true)
        val updater = TriggerAudioResponseUpdater(repository, fileWriter, fileDeleter)

        every { repository.find(audioResponse.id) } returns audioResponse.copy().right()
        every { fileDeleter.delete(audioResponse.path) } returns Unit.right()
        every { fileWriter.write(newFileContent, newAudioResponse.path) } returns Unit.right()

        val result = updater.update(
            TriggerAudioResponseUpdateRequest(
                id = audioResponse.id.value,
                values = TriggerAudioResponseUpdateRequest.NewValues(
                    triggerId = newAudioResponse.trigger.value,
                    guildId = newAudioResponse.guild.value,
                    fileName = newAudioResponse.sourceUri.value,
                    content = newFileContent
                )
            )
        )

        assertTrue(result.isRight())

        verify { fileDeleter.delete(audioResponse.path) }
        verify { fileWriter.write(newFileContent, newAudioResponse.path) }
        verify { repository.save(newAudioResponse) }
    }

}
