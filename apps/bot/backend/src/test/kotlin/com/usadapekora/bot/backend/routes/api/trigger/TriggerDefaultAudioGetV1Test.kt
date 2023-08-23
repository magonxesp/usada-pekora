package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.uglifyJson
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.triggerAudioFilePath
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
        val destination = triggerAudioFilePath(
            guildId = Guild.GuildId("d04c17e7-ff23-4c9d-ab29-1b19c1ed6f55"),
            triggerId = Trigger.TriggerId("c2a05313-b765-4be0-bf92-0b77136d033b"),
            fileName = "assets_audio_Its_me_pekora.mp3"
        )

        createAudioDummy(
            id = audioId,
            triggerId = "c2a05313-b765-4be0-bf92-0b77136d033b",
            guildId = "d04c17e7-ff23-4c9d-ab29-1b19c1ed6f55",
        )

        val expectedBody = """
            {
                "id": "$audioId",
                "kind": "file",
                "source": "${destination.replace("\\", "\\\\")}"
            }
        """.uglifyJson()

        val response = client.get("/api/v1/trigger/response/audio/$audioId") {
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
