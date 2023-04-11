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

class TriggerDefaultAudioResponseDeleteApiControllerTest : SpringBootHttpTestCase() {

    private fun uploadAudioFileRequest(audioId: String, triggerId: String, guildId: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/v1/trigger/audio")
                .file(MockMultipartFile("file", readResource("/assets_audio_Its_me_pekora.mp3")))
                .param("id", audioId)
                .param("triggerId", triggerId)
                .param("guildId", guildId)
                .accept(MediaType.APPLICATION_JSON)
    )

    private fun deleteRequest(audioId: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/trigger/audio/$audioId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should delete trigger audio`() {
        val id = UUID.randomUUID().toString()

        uploadAudioFileRequest(
            audioId = id,
            triggerId = UUID.randomUUID().toString(),
            guildId = "94101459"
        ).andExpect {
            assertEquals(HttpStatus.CREATED.value(), it.response.status)
        }

        deleteRequest(id).andExpect {
            assertEquals(HttpStatus.OK.value(), it.response.status)
        }
    }

    @Test
    fun `should not delete not existing trigger audio`() {
        deleteRequest(UUID.randomUUID().toString()).andExpect {
            assertEquals(HttpStatus.BAD_REQUEST.value(), it.response.status)
        }
    }

}
