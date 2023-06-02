package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreator
import com.usadapekora.bot.application.trigger.delete.audio.TriggerDefaultAudioDeleter
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFinder
import com.usadapekora.bot.application.trigger.read.TriggerDefaultAudioReader
import com.usadapekora.bot.application.trigger.update.audio.TriggerDefaultAudioResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.audio.TriggerDefaultAudioResponseUpdater
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.shared.infrastructure.common.ktor.respondError
import com.usadapekora.shared.infrastructure.common.ktor.toFormData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val triggerAudioFinder: TriggerDefaultAudioFinder by inject(TriggerDefaultAudioFinder::class.java)
private val triggerDefaultAudioReader: TriggerDefaultAudioReader by inject(TriggerDefaultAudioReader::class.java)
private val triggerDefaultAudioDeleter: TriggerDefaultAudioDeleter by inject(TriggerDefaultAudioDeleter::class.java)
private val triggerDefaultAudioResponseCreator: TriggerDefaultAudioResponseCreator by inject(TriggerDefaultAudioResponseCreator::class.java)
private val triggerDefaultAudioResponseUpdater: TriggerDefaultAudioResponseUpdater by inject(TriggerDefaultAudioResponseUpdater::class.java)

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
    route("/api/v1/trigger/response/audio") {
        get("/{id}") {
            triggerAudioFinder.find(call.parameters["id"] ?: "")
                .onLeft { return@get call.respondError(errorStatusCode(it), it.message ?: "") }
                .onRight { call.respond(it) }
        }
        get("/{id}/content") {
            triggerDefaultAudioReader.read(call.parameters["id"] ?: "")
                .onLeft { return@get call.respondError(errorStatusCode(it), it.message ?: "") }
                .onRight { call.respondBytes(it) }
        }
        post {
            val formData = call.receiveMultipart().toFormData()
            val file = formData.getFile("file") ?: return@post call.respondError(HttpStatusCode.BadRequest, "The file parameter is missing")

            val request = TriggerDefaultAudioResponseCreateRequest(
                id = formData.getString("id") ?: return@post call.respondError(HttpStatusCode.BadRequest, "The id parameter is missing"),
                triggerId = formData.getString("triggerId") ?: return@post call.respondError(HttpStatusCode.BadRequest, "The triggerId parameter is missing"),
                guildId = formData.getString("guildId") ?: return@post call.respondError(HttpStatusCode.BadRequest, "The guildId parameter is missing"),
                fileName = file.fileName,
                content = file.content
            )

            triggerDefaultAudioResponseCreator.create(request)
                .onLeft { return@post call.respondError(errorStatusCode(it), it.message ?: "") }
                .onRight { call.respond(HttpStatusCode.Created) }
        }
        put("/{id}") {
            val formData = call.receiveMultipart().toFormData()
            val file = formData.getFile("file")

            val request = TriggerDefaultAudioResponseUpdateRequest(
                id = call.parameters["id"] ?: "",
                values = TriggerDefaultAudioResponseUpdateRequest.NewValues(
                    fileName = file?.fileName,
                    triggerId = formData.getString("triggerId"),
                    guildId = formData.getString("guildId"),
                    content = file?.content
                )
            )

            triggerDefaultAudioResponseUpdater.update(request)
                .onLeft { return@put call.respondError(errorStatusCode(it), it.message ?: "") }
                .onRight { call.respond(HttpStatusCode.OK) }
        }
        delete("/{id}") {
            triggerDefaultAudioDeleter.delete(call.parameters["id"] ?: "")
                .onLeft { return@delete call.respondError(errorStatusCode(it), it.message ?: "") }
                .onRight { call.respond(HttpStatusCode.OK) }
        }
    }
}
