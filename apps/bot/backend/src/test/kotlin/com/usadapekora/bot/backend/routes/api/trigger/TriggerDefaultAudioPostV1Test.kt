package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.HttpTestCase
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerDefaultAudioPostV1Test : HttpTestCase() {

    @Test
    fun `should upload file and save it`() = withTestApplication {
        val response = client.post("/api/v1/trigger/response/audio") {
            contentType(ContentType.MultiPart.FormData)
            accept(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(
                formData {
                    append("id", UUID.randomUUID().toString())
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
    }

    @Test
    fun `should not upload same file`() = withTestApplication {
        val audioId = UUID.randomUUID().toString()
        val triggerId = UUID.randomUUID().toString()

        var response = client.post("/api/v1/trigger/response/audio") {
            contentType(ContentType.MultiPart.FormData)
            accept(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(
                formData {
                    append("id", audioId)
                    append("triggerId", triggerId)
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

        response = client.post("/api/v1/trigger/response/audio") {
            contentType(ContentType.MultiPart.FormData)
            accept(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(
                formData {
                    append("id", audioId)
                    append("triggerId", triggerId)
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

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

}
