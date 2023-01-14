package com.usadapekora.context.application.trigger

import com.usadapekora.context.domain.shared.file.UploadedFileWriter
import com.usadapekora.context.domain.TriggerAudioMother
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
import io.mockk.mockk
import io.mockk.verify
import java.io.File
import kotlin.test.Test

class TriggerAudioCreatorTest {

    @Test
    fun `should create trigger audio and save file`() {
//        val repository = mockk<TriggerAudioRepository>()
//        val writer = mockk<UploadedFileWriter>()
//        val creator = TriggerAudioCreator(repository, writer)
//
//        val expected = TriggerAudioMother.create()
//        val file = File("audio.mp3")
//
//        creator.create(TriggerAudioCreateRequest(expected.id.value, expected.trigger.value, file))
//
//        verify { repository.save(expected) }
//        verify { writer.write(file, "/destination/path") }
    }

}
