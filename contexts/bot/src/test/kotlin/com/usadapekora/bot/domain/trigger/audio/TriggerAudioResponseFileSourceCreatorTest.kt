package com.usadapekora.bot.domain.trigger.audio

import com.usadapekora.bot.domain.FileMother
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerAudioResponseFileSourceCreatorTest {

    @Test
    fun `it should create a trigger response audio with file source`() {
        val factory = TriggerAudioResponseFileSourceCreator()
        val trigger = TriggerMother.create()
        val fileName = FileMother.filename(".mp3")
        val audio = TriggerAudioResponseMother.create(
            source = TriggerAudioResponse.TriggerAudioResponseSource.FILE.name,
            sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(trigger.guildId!!, trigger.id, fileName)
        )
        val content = TriggerAudioResponseFileContent(
            fileContent = Random.nextBytes(10),
            fileName = fileName
        )

        val result = factory.create(audio.id(), trigger.id(), trigger.guildId!!.value, content)

        assertEquals(audio, result.getOrNull(), result.leftOrNull()?.message)
    }

}
