package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.TriggerModuleUnitTestCase
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreateRequest
import com.usadapekora.bot.domain.FileMother
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import io.mockk.every
import io.mockk.verify
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertIs

class TriggerAudioResponseCreatorTest : TriggerModuleUnitTestCase() {

    @Test
    fun `it should save a new trigger audio`() {
        val trigger = TriggerMother.create()
        val file = Random.Default.nextBytes(10)
        val fileName = FileMother.filename(".mp3")
        val audioResponse = TriggerAudioResponseMother.create(
            source = "file",
            sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(trigger.guildId!!, trigger.id, fileName)
        )
        val request = TriggerAudioResponseCreateRequest(
            id = audioResponse.id.value,
            triggerId = trigger.id.value,
            guildId = trigger.guildId!!.value,
            content = TriggerAudioResponseFileContent(
                fileName = fileName,
                fileContent = file
            )
        )

        every { responseAudioRepository.find(audioResponse.id) } returns TriggerAudioResponseException.NotFound().left()
        `should return writer and creator by content`(request.content)
        `should return audio by request`(audioResponse, request)
        `should write audio file`(audioResponse, request.content)

        val result = creator.create(request)

        verify { responseAudioRepository.save(audioResponse) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

    @Test
    fun `it should not save a existing trigger audio`() {
        val trigger = TriggerMother.create()
        val file = Random.Default.nextBytes(10)
        val fileName = FileMother.filename(".mp3")
        val audioResponse = TriggerAudioResponseMother.create(
            source = "file",
            sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(trigger.guildId!!, trigger.id, fileName)
        )
        val request = TriggerAudioResponseCreateRequest(
            id = audioResponse.id.value,
            triggerId = trigger.id.value,
            guildId = trigger.guildId!!.value,
            content = TriggerAudioResponseFileContent(
                fileName = fileName,
                fileContent = file
            )
        )

        every { responseAudioRepository.find(audioResponse.id) } returns audioResponse.right()

        val result = creator.create(request)

        verify(inverse = true) { responseAudioRepository.save(audioResponse) }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

}
