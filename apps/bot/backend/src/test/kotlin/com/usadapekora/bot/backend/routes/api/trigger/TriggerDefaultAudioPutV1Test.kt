package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.controller.api.v1.trigger.TriggerControllerTest
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.server.testing.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerDefaultAudioPutV1Test : TriggerControllerTest() {

    @Test
    fun `should update file and save it`() = testApplication {
        val id = UUID.randomUUID().toString()
        createAudioDummy(id = id)

        val response = client.put("/api/v1/trigger/response/audio/$id") {
            contentType(ContentType.MultiPart.FormData)
            accept(ContentType.Application.Json)
            formData {
                append("triggerId", UUID.randomUUID().toString())
                append("guildId", "94101459")
                append("file", readResource("/assets_audio_Its_me_pekora.mp3"))
            }
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `should not update non existing audio file`() = testApplication {
        val response = client.put("/api/v1/trigger/response/audio/${UUID.randomUUID()}") {
            contentType(ContentType.MultiPart.FormData)
            accept(ContentType.Application.Json)
            formData {
                append("triggerId", UUID.randomUUID().toString())
                append("guildId", "94101459")
                append("file", readResource("/assets_audio_Its_me_pekora.mp3"))
            }
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

}
