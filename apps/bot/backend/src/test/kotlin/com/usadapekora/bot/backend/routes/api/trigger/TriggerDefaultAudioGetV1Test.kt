package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.uglifyJson
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class TriggerDefaultAudioGetV1Test : TriggerTest() {

    @Test
    fun `should find a trigger audio`() = withTestApplication {
        val audioId = UUID.randomUUID().toString()
        val audio = TriggerDefaultAudioResponse.fromPrimitives(
            id = audioId,
            trigger = "c2a05313-b765-4be0-bf92-0b77136d033b",
            guild = "47541556",
            file = "assets_audio_Its_me_pekora.mp3"
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
                "file": "assets_audio_Its_me_pekora.mp3"
            }
        """.uglifyJson()

        val response = client.get("/api/v1/trigger/response/audio/${audio.id.value}") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expectedBody, response.bodyAsText())
    }

    @Test
    fun `should not find a trigger audio`() = withTestApplication {
        val response = client.get("/api/v1/trigger/response/audio/e322b3ac-2d30-4eff-afdc-3504f66ac4ba") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `should get the file contents of trigger audio`() = withTestApplication {
        val audioId = UUID.randomUUID().toString()
        createAudioDummy(id = audioId)

        val response = client.get("/api/v1/trigger/response/audio/$audioId/content") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertContentEquals(readResource("/assets_audio_Its_me_pekora.mp3"), response.readBytes())
    }

    @Test
    fun `should not get the file contents of trigger audio does not exists`() = withTestApplication {
        val response = client.get("/api/v1/trigger/response/audio/e322b3ac-2d30-4eff-afdc-3504f66ac4ba/content") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

}
