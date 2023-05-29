package com.usadapekora.bot.backend.controller.api.v1.trigger.audio

import com.usadapekora.bot.backend.controller.api.v1.trigger.TriggerControllerTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerDefaultAudioResponsePutApiControllerTest : TriggerControllerTest() {

    private fun updateAudioFileRequest(audioId: String, triggerId: String, guildId: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/api/v1/trigger/response/audio/$audioId")
                .file(MockMultipartFile("file", readResource("/assets_audio_Its_me_pekora.mp3")))
                .param("triggerId", triggerId)
                .param("guildId", guildId)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should update file and save it`() {
        val id = UUID.randomUUID().toString()
        createAudioDummy(id = id)

        updateAudioFileRequest(
            audioId = id,
            triggerId = UUID.randomUUID().toString(),
            guildId = "94101459"
        ).andExpect {
            assertEquals(HttpStatus.OK.value(), it.response.status)
        }
    }

    @Test
    fun `should not update non existing audio file`() {
        updateAudioFileRequest(
            audioId = UUID.randomUUID().toString(),
            triggerId = UUID.randomUUID().toString(),
            guildId = "94101459"
        ).andExpect {
            assertEquals(HttpStatus.BAD_REQUEST.value(), it.response.status)
        }
    }

}
