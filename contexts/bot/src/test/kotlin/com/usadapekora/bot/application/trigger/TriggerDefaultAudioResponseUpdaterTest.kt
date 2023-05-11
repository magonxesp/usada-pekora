package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.application.trigger.update.audio.TriggerDefaultAudioResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.audio.TriggerDefaultAudioResponseUpdater
import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import com.usadapekora.bot.domain.shared.file.DomainFileWriter
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.random.Random
import kotlin.test.Test

class TriggerDefaultAudioResponseUpdaterTest {

    @Test
    fun `it should update a trigger default audio response`() {
        val newFileContent = Random.nextBytes(10)
        val audioResponse = TriggerAudioDefaultMother.create()
        val newAudioResponse = TriggerAudioDefaultMother.create(id = audioResponse.id.value)
        val repository = mockk<TriggerAudioDefaultRepository>(relaxed = true)
        val fileWriter = mockk<DomainFileWriter>(relaxed = true)
        val fileDeleter = mockk<DomainFileDeleter>(relaxed = true)
        val updater = TriggerDefaultAudioResponseUpdater(repository, fileWriter, fileDeleter)

        every { repository.find(audioResponse.id) } returns audioResponse.right()

        updater.update(
            TriggerDefaultAudioResponseUpdateRequest(
                id = audioResponse.id.value,
                values = TriggerDefaultAudioResponseUpdateRequest.NewValues(
                    triggerId = newAudioResponse.trigger.value,
                    guildId = newAudioResponse.guild.value,
                    fileName = newAudioResponse.file.value,
                    content = newFileContent
                )
            )
        )

        verify { fileWriter.write(newFileContent, newAudioResponse.path) }
        verify { repository.save(newAudioResponse) }
    }

    @Test
    fun `it should remove the old file when the path changes`() {
        val newFileContent = Random.nextBytes(10)
        val audioResponse = TriggerAudioDefaultMother.create()
        val newAudioResponse = TriggerAudioDefaultMother.create(id = audioResponse.id.value)
        val repository = mockk<TriggerAudioDefaultRepository>(relaxed = true)
        val fileWriter = mockk<DomainFileWriter>(relaxed = true)
        val fileDeleter = mockk<DomainFileDeleter>(relaxed = true)
        val updater = TriggerDefaultAudioResponseUpdater(repository, fileWriter, fileDeleter)

        every { repository.find(audioResponse.id) } returns audioResponse.copy().right()

        updater.update(
            TriggerDefaultAudioResponseUpdateRequest(
                id = audioResponse.id.value,
                values = TriggerDefaultAudioResponseUpdateRequest.NewValues(
                    triggerId = newAudioResponse.trigger.value,
                    guildId = newAudioResponse.guild.value,
                    fileName = newAudioResponse.file.value,
                    content = newFileContent
                )
            )
        )

        verify { fileDeleter.delete(audioResponse.path) }
        verify { fileWriter.write(newFileContent, newAudioResponse.path) }
        verify { repository.save(newAudioResponse) }
    }

}
