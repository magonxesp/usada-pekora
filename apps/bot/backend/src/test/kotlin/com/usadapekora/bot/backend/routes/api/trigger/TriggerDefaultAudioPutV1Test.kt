package com.usadapekora.bot.backend.routes.api.trigger

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.server.testing.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerDefaultAudioPutV1Test : TriggerTest() {

    @Test
    fun `should update file and save it`() = withTestApplication {
        val id = UUID.randomUUID().toString()
        createAudioDummy(id = id)

        val response = client.put("/api/v1/trigger/response/audio/$id") {
            contentType(ContentType.MultiPart.FormData)
            accept(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(
                formData {
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

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `should not update non existing audio file`() = withTestApplication {
        val response = client.put("/api/v1/trigger/response/audio/${UUID.randomUUID()}") {
            contentType(ContentType.MultiPart.FormData)
            accept(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(
                formData {
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

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

}
