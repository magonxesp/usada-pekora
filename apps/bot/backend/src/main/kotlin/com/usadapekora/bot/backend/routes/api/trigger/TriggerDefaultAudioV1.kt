package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreator
import com.usadapekora.bot.application.trigger.delete.audio.TriggerAudioResponseDeleter
import com.usadapekora.bot.application.trigger.find.audio.TriggerAudioResponseFinder
import com.usadapekora.bot.application.trigger.read.TriggerDefaultAudioReader
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUpdater
import com.usadapekora.bot.backend.testMode
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.shared.infrastructure.ktor.respondError
import com.usadapekora.shared.infrastructure.ktor.toFormData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private fun errorStatusCode(error: Any) = when(error) {
    is TriggerAudioResponseException.FailedToDelete -> HttpStatusCode.InternalServerError
    is TriggerAudioResponseException.AlreadyExists -> HttpStatusCode.BadRequest
    is TriggerAudioResponseException.FailedToRead -> HttpStatusCode.InternalServerError
    is TriggerAudioResponseException.FailedToSave -> HttpStatusCode.InternalServerError
    is TriggerAudioResponseException.FailedToUpdate -> HttpStatusCode.InternalServerError
    is TriggerAudioResponseException.NotFound -> HttpStatusCode.NotFound
    else -> HttpStatusCode.InternalServerError
}

fun Route.triggerDefaultAudioV1() {
    authenticate(optional = environment?.testMode ?: false) {
        route("/api/v1/trigger/response/audio") {
            get("/{id}") {
                val triggerAudioFinder: TriggerAudioResponseFinder by inject(TriggerAudioResponseFinder::class.java)

                triggerAudioFinder.find(call.parameters["id"] ?: "")
                    .onLeft { return@get call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respond(it) }
            }
            get("/{id}/content") {
                val triggerDefaultAudioReader: TriggerDefaultAudioReader by inject(TriggerDefaultAudioReader::class.java)

                triggerDefaultAudioReader.read(call.parameters["id"] ?: "")
                    .onLeft { return@get call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respondBytes(it) }
            }
            post {
                val triggerAudioResponseCreator: TriggerAudioResponseCreator by inject(
                    TriggerAudioResponseCreator::class.java
                )
                val formData = call.receiveMultipart().toFormData()
                val file = formData.getFile("file") ?: return@post call.respondError(
                    HttpStatusCode.BadRequest,
                    "The file parameter is missing"
                )

                val request = TriggerAudioResponseCreateRequest(
                    id = formData.getString("id") ?: return@post call.respondError(
                        HttpStatusCode.BadRequest,
                        "The id parameter is missing"
                    ),
                    triggerId = formData.getString("triggerId") ?: return@post call.respondError(
                        HttpStatusCode.BadRequest,
                        "The triggerId parameter is missing"
                    ),
                    guildId = formData.getString("guildId") ?: return@post call.respondError(
                        HttpStatusCode.BadRequest,
                        "The guildId parameter is missing"
                    ),
                    fileName = file.fileName,
                    content = file.content
                )

                triggerAudioResponseCreator.create(request)
                    .onLeft { return@post call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respond(HttpStatusCode.Created) }
            }
            put("/{id}") {
                val triggerAudioResponseUpdater: TriggerAudioResponseUpdater by inject(
                    TriggerAudioResponseUpdater::class.java
                )
                val formData = call.receiveMultipart().toFormData()
                val file = formData.getFile("file")

                val request = TriggerAudioResponseUpdateRequest(
                    id = call.parameters["id"] ?: "",
                    values = TriggerAudioResponseUpdateRequest.NewValues(
                        fileName = file?.fileName,
                        triggerId = formData.getString("triggerId"),
                        guildId = formData.getString("guildId"),
                        content = file?.content
                    )
                )

                triggerAudioResponseUpdater.update(request)
                    .onLeft { return@put call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respond(HttpStatusCode.OK) }
            }
            delete("/{id}") {
                val triggerAudioResponseDeleter: TriggerAudioResponseDeleter by inject(TriggerAudioResponseDeleter::class.java)

                triggerAudioResponseDeleter.delete(call.parameters["id"] ?: "")
                    .onLeft { return@delete call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respond(HttpStatusCode.OK) }
            }
        }
    }
}
