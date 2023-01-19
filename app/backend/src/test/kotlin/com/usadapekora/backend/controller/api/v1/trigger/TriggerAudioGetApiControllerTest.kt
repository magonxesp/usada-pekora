package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.SpringBootHttpTestCase
import com.usadapekora.backend.uglifyJson
import com.usadapekora.context.application.trigger.create.TriggerAudioCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerAudioCreator
import com.usadapekora.context.domain.trigger.TriggerAudio
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals


class TriggerAudioGetApiControllerTest : SpringBootHttpTestCase() {

    private fun request(url: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should find a trigger audio`() {
        val creator: TriggerAudioCreator by inject(TriggerAudioCreator::class.java)
        val audioId = UUID.randomUUID().toString()
        val audio = TriggerAudio.fromPrimitives(
            id = audioId,
            trigger = "c2a05313-b765-4be0-bf92-0b77136d033b",
            guild = "47541556"
        )

        creator.create(TriggerAudioCreateRequest(
            id = audio.id.value,
            triggerId = audio.trigger.value,
            guildId = audio.guild.value,
            fileName = "assets_audio_Its_me_pekora.mp3",
            content = readResource("/assets_audio_Its_me_pekora.mp3"),
        ))

        val expectedBody = """
            {
                "id": "$audioId",
                "triggerId": "c2a05313-b765-4be0-bf92-0b77136d033b",
                "guildId": "47541556"
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

}
