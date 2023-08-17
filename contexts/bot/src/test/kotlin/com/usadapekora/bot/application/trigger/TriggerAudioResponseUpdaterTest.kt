package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUpdater
import com.usadapekora.bot.domain.FileMother
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseSourceUriFactory
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileWriter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.io.File
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class TriggerAudioResponseUpdaterTest {

    @Test
    fun `it should update a trigger default audio response`() {
        val newFileContent = Random.nextBytes(10)
        val trigger = TriggerMother.create()
        val audioResponse = TriggerAudioResponseMother.create(sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(
            guildId = trigger.guildId!!,
            triggerId = trigger.id,
            FileMother.filename(".mp3")
        ))
        val newAudioResponse = TriggerAudioResponseMother.create(
            id = audioResponse.id.value,
            sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(
                guildId = trigger.guildId!!,
                triggerId = trigger.id,
                FileMother.filename(".mp3")
            )
        )
        val audioResponseFilePath = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(audioResponse.sourceUri.value).getOrNull()!!
        val newAudioResponseFilePath = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(newAudioResponse.sourceUri.value).getOrNull()!!
        val repository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val fileWriter = mockk<DomainFileWriter>(relaxed = true)
        val fileDeleter = mockk<DomainFileDeleter>(relaxed = true)
        val updater = TriggerAudioResponseUpdater(repository, fileWriter, fileDeleter)

        every { repository.find(audioResponse.id) } returns audioResponse.right()
        every { fileDeleter.delete(audioResponseFilePath) } returns Unit.right()
        every { fileWriter.write(newFileContent, newAudioResponseFilePath) } returns Unit.right()

        val result = updater.update(
            TriggerAudioResponseUpdateRequest(
                id = audioResponse.id.value,
                values = TriggerAudioResponseUpdateRequest.NewValues(
                    triggerId = trigger.id.value,
                    guildId = trigger.guildId!!.value,
                    fileName = File(newAudioResponseFilePath).name,
                    content = newFileContent
                )
            )
        )

        assertTrue(result.isRight())

        verify { fileWriter.write(newFileContent, newAudioResponseFilePath) }
        verify { repository.save(newAudioResponse) }
    }

    @Test
    fun `it should remove the old file when the path changes`() {
        val newFileContent = Random.nextBytes(10)
        val trigger = TriggerMother.create()
        val audioResponse = TriggerAudioResponseMother.create(sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(
            guildId = trigger.guildId!!,
            triggerId = trigger.id,
            FileMother.filename(".mp3")
        ))
        val newAudioResponse = TriggerAudioResponseMother.create(
            id = audioResponse.id.value,
            sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(
                guildId = trigger.guildId!!,
                triggerId = trigger.id,
                FileMother.filename(".mp3")
            )
        )
        val audioResponseFilePath = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(audioResponse.sourceUri.value).getOrNull()!!
        val newAudioResponseFilePath = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(newAudioResponse.sourceUri.value).getOrNull()!!
        val repository = mockk<TriggerAudioResponseRepository>(relaxed = true)
        val fileWriter = mockk<DomainFileWriter>(relaxed = true)
        val fileDeleter = mockk<DomainFileDeleter>(relaxed = true)
        val updater = TriggerAudioResponseUpdater(repository, fileWriter, fileDeleter)

        every { repository.find(audioResponse.id) } returns audioResponse.copy().right()
        every { fileDeleter.delete(audioResponseFilePath) } returns Unit.right()
        every { fileWriter.write(newFileContent, newAudioResponseFilePath) } returns Unit.right()

        val result = updater.update(
            TriggerAudioResponseUpdateRequest(
                id = audioResponse.id.value,
                values = TriggerAudioResponseUpdateRequest.NewValues(
                    triggerId = trigger.id.value,
                    guildId = trigger.guildId!!.value,
                    fileName = File(newAudioResponseFilePath).name,
                    content = newFileContent
                )
            )
        )

        assertTrue(result.isRight())

        verify { fileDeleter.delete(audioResponseFilePath) }
        verify { fileWriter.write(newFileContent, newAudioResponseFilePath) }
        verify { repository.save(newAudioResponse) }
    }

}
