package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.uglifyJson
import com.usadapekora.bot.domain.trigger.TriggerAudio
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


class TriggerAudioGetApiControllerTest : TriggerControllerTest() {

    private fun request(url: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should find a trigger audio`() {
        val audioId = UUID.randomUUID().toString()
        val audio = TriggerAudio.fromPrimitives(
            id = audioId,
            trigger = "c2a05313-b765-4be0-bf92-0b77136d033b",
            guild = "47541556",
            file = "c2a05313-b765-4be0-bf92-0b77136d033b.mp3"
        )

        createAudioDummy(
            id = audio.id.value,
            triggerId = audio.trigger.value,
            guildId = audio.guild.value,
        )

        val expectedBody = """
            {
                "id": "$audioId",
                "triggerId": "c2a05313-b765-4be0-bf92-0b77136d033b",
                "guildId": "47541556",
                "file": "$audioId.mp3"
            }
        """.uglifyJson()

        request("/api/v1/trigger/audio/${audio.id.value}").andExpect {
            assertEquals(200, it.response.status)
            assertEquals(expectedBody, it.response.contentAsString)
        }
    }

    @Test
    fun `should not find a trigger audio`() {
        request("/api/v1/trigger/audio/e322b3ac-2d30-4eff-afdc-3504f66ac4ba").andExpect {
            assertEquals(404, it.response.status)
        }
    }

    @Test
    fun `should get the file contents of trigger audio`() {
        val audioId = UUID.randomUUID().toString()
        createAudioDummy(id = audioId)

        request("/api/v1/trigger/audio/$audioId/content").andExpect {
            assertEquals(200, it.response.status)
            assertContentEquals(readResource("/assets_audio_Its_me_pekora.mp3"), it.response.contentAsByteArray)
        }
    }

    @Test
    fun `should not get the file contents of trigger audio does not exists`() {
        request("/api/v1/trigger/audio/e322b3ac-2d30-4eff-afdc-3504f66ac4ba/content").andExpect {
            assertEquals(404, it.response.status)
        }
    }

}
