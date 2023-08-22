package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.TriggerModuleUnitTestCase
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseFileCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseUrlCreateRequest
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.triggerAudioFilePath
import io.mockk.every
import io.mockk.verify
import java.util.UUID
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertIs

class TriggerAudioResponseCreatorTest : TriggerModuleUnitTestCase() {

    @Test
    fun `it should save a new trigger audio of type file`() {
        val responseAudio = TriggerAudioResponseMother.create(kind = TriggerAudioResponse.TriggerAudioResponseKind.FILE.name)
        val request = TriggerAudioResponseFileCreateRequest(
            id = responseAudio.id(),
            triggerId = UUID.randomUUID().toString(),
            guildId = UUID.randomUUID().toString(),
            fileName = responseAudio.source.value,
            fileContent = Random.nextBytes(10)
        )

        responseAudio.source = TriggerAudioResponse.TriggerAudioResponseSource(triggerAudioFilePath(
            guildId = Guild.GuildId(request.guildId),
            triggerId = Trigger.TriggerId(request.triggerId),
            fileName = request.fileName
        ))

        val destinationPath = triggerAudioFilePath(
            guildId = Guild.GuildId(request.guildId),
            triggerId = Trigger.TriggerId(request.triggerId),
            fileName = request.fileName
        )

        every { responseAudioRepository.find(responseAudio.id) } returns TriggerAudioResponseException.NotFound().left()
        every { domainFileWriter.write(request.fileContent, destinationPath) } returns Unit.right()

        val result = creator.create(request)

        verify { responseAudioRepository.find(responseAudio.id) }
        verify { domainFileWriter.write(request.fileContent, destinationPath) }
        verify { responseAudioRepository.save(responseAudio) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

    @Test
    fun `it should create new trigger audio response with url source`() {
        val responseAudio = TriggerAudioResponseMother.create(kind = TriggerAudioResponse.TriggerAudioResponseKind.RESOURCE.name)
        val request = TriggerAudioResponseUrlCreateRequest(
            id = responseAudio.id(),
            type = responseAudio.kind.name.lowercase(),
            source = responseAudio.source.value
        )

        every { responseAudioRepository.find(responseAudio.id) } returns TriggerAudioResponseException.NotFound().left()

        val result = creator.create(request)

        verify { responseAudioRepository.find(responseAudio.id) }
        verify(inverse = true) { domainFileWriter.write(any(), any()) }
        verify { responseAudioRepository.save(responseAudio) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

    @Test
    fun `it should not save a existing trigger audio`() {
        val responseAudio = TriggerAudioResponseMother.create()
        val request = TriggerAudioResponseFileCreateRequest(
            id = responseAudio.id(),
            triggerId = UUID.randomUUID().toString(),
            guildId = UUID.randomUUID().toString(),
            fileName = responseAudio.source.value,
            fileContent = Random.nextBytes(10)
        )

        every { responseAudioRepository.find(responseAudio.id) } returns responseAudio.right()

        val result = creator.create(request)

        verify { responseAudioRepository.find(responseAudio.id) }
        verify(inverse = true) { domainFileWriter.write(any(), any()) }
        verify(inverse = true) { responseAudioRepository.save(responseAudio) }

        assertIs<TriggerAudioResponseException.AlreadyExists>(result.leftOrNull())
    }

}
