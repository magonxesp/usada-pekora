package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.SpringBootHttpTestCase
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerDefaultAudioResponsePostApiControllerTest : SpringBootHttpTestCase() {

    private fun uploadAudioFileRequest(audioId: String, triggerId: String, guildId: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/v1/trigger/audio")
                .file(MockMultipartFile("file", readResource("/assets_audio_Its_me_pekora.mp3")))
                .param("id", audioId)
                .param("triggerId", triggerId)
                .param("guildId", guildId)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should upload file and save it`() {
        uploadAudioFileRequest(
            audioId = UUID.randomUUID().toString(),
            triggerId = UUID.randomUUID().toString(),
            guildId = "94101459"
        ).andExpect {
            assertEquals(HttpStatus.CREATED.value(), it.response.status)
        }
    }

    @Test
    fun `should not upload same file`() {
        val audioId = UUID.randomUUID().toString()
        val triggerId = UUID.randomUUID().toString()

        uploadAudioFileRequest(
            audioId = audioId,
            triggerId = triggerId,
            guildId = "94101459"
        ).andExpect {
            assertEquals(HttpStatus.CREATED.value(), it.response.status)
        }

        uploadAudioFileRequest(
            audioId = audioId,
            triggerId = triggerId,
            guildId = "94101459"
        ).andExpect {
            assertEquals(HttpStatus.BAD_REQUEST.value(), it.response.status)
        }
    }

}
