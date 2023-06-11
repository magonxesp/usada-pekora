package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.HttpTestCase
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerDefaultAudioDeleteV1Test : HttpTestCase() {

    @Test
    fun `should delete trigger audio`() = withTestApplication {
        val id = UUID.randomUUID().toString()

        var response = client.post("/api/v1/trigger/response/audio") {
            contentType(ContentType.MultiPart.FormData)
            accept(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(
                formData {
                    append("id", id)
                    append("triggerId", UUID.randomUUID().toString())
                    append("guildId", "94101459")
                    append(
                        key = "file",
                        value = readResource("/assets_audio_Its_me_pekora.mp3"),
                        headers = headers {
                            append(HttpHeaders.ContentType, ContentType.Audio.MPEG)
                            append(HttpHeaders.ContentDisposition, "filename=assets_audio_Its_me_pekora.mp3")
                        }.build()
                    )
                }
            ))
        }

        assertEquals(HttpStatusCode.Created, response.status)

        response = client.delete("/api/v1/trigger/response/audio/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `should not delete not existing trigger audio`() = withTestApplication {
        val response = client.delete("/api/v1/trigger/response/audio/${UUID.randomUUID()}") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

}
