package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.TriggerModuleUnitTestCase
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseFileUpdateRequest
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUrlUpdateRequest
import com.usadapekora.bot.domain.FileMother
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.triggerAudioFilePath
import io.mockk.every
import io.mockk.verify
import org.litote.kmongo.descending
import java.util.UUID
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertIs

class TriggerAudioResponseUpdaterTest : TriggerModuleUnitTestCase() {

    @Test
    fun `it should update a existing audio response with file source`() {
        val responseAudio = TriggerAudioResponseMother.create(
            kind = TriggerAudioResponse.TriggerAudioResponseKind.FILE.name.lowercase()
        )
        val request = TriggerAudioResponseFileUpdateRequest(
            id = responseAudio.id(),
            values = TriggerAudioResponseFileUpdateRequest.NewValues(
                triggerId = UUID.randomUUID().toString(),
                guildId = UUID.randomUUID().toString(),
                fileName = FileMother.filename(".mp3"),
                fileContent = Random.nextBytes(10)
            )
        )

        val newFileDestination = triggerAudioFilePath(
            guildId = Guild.GuildId(request.values.guildId),
            triggerId = Trigger.TriggerId(request.values.triggerId),
            fileName = request.values.fileName
        )

        every { responseAudioRepository.find(responseAudio.id) } returns responseAudio.copy().right()
        every { domainFileWriter.write(request.values.fileContent, newFileDestination) } returns Unit.right()
        every { domainFileDeleter.delete(responseAudio.source.value) } returns Unit.right()

        val updatedAudioResponse = responseAudio.copy(
            kind = TriggerAudioResponse.TriggerAudioResponseKind.FILE,
            source = TriggerAudioResponse.TriggerAudioResponseSource(newFileDestination)
        )

        val result = updater.update(request)

        verify { responseAudioRepository.find(responseAudio.id) }
        verify { domainFileWriter.write(request.values.fileContent, newFileDestination) }
        verify { domainFileDeleter.delete(responseAudio.source.value) }
        verify { responseAudioRepository.save(updatedAudioResponse) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

    @Test
    fun `it should update a existing audio response with resource source`() {
        val responseAudio = TriggerAudioResponseMother.create(
            kind = TriggerAudioResponse.TriggerAudioResponseKind.FILE.name.lowercase()
        )
        val request = TriggerAudioResponseUrlUpdateRequest(
            id = responseAudio.id(),
            values = TriggerAudioResponseUrlUpdateRequest.NewValues(
                type = TriggerAudioResponse.TriggerAudioResponseKind.RESOURCE.name.lowercase(),
                source = "resource/path.mp3"
            )
        )

        every { responseAudioRepository.find(responseAudio.id) } returns responseAudio.copy().right()
        every { domainFileDeleter.delete(responseAudio.source.value) } returns Unit.right()

        val updatedAudioResponse = responseAudio.copy(
            kind = TriggerAudioResponse.TriggerAudioResponseKind.RESOURCE,
            source = TriggerAudioResponse.TriggerAudioResponseSource(request.values.source)
        )

        val result = updater.update(request)

        verify { responseAudioRepository.find(responseAudio.id) }
        verify(inverse = true) { domainFileWriter.write(any(), any()) }
        verify { domainFileDeleter.delete(responseAudio.source.value) }
        verify { responseAudioRepository.save(updatedAudioResponse) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

}
