package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.application.trigger.delete.TriggerDeleter
import com.usadapekora.bot.application.trigger.find.TriggerFinder
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFinder
import com.usadapekora.bot.application.trigger.update.TriggerUpdateRequest
import com.usadapekora.bot.application.trigger.update.TriggerUpdater
import com.usadapekora.bot.backend.testMode
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.shared.infrastructure.ktor.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val finder: TriggerFinder by inject(TriggerFinder::class.java)
private val audioFinder: TriggerDefaultAudioFinder by inject(TriggerDefaultAudioFinder::class.java)
private val triggerCreator: TriggerCreator by inject(TriggerCreator::class.java)
private val updater: TriggerUpdater by inject(TriggerUpdater::class.java)
private val deleter: TriggerDeleter by inject(TriggerDeleter::class.java)

private fun errorStatusCode(error: Any) = when(error) {
    is TriggerException.NotFound -> HttpStatusCode.NotFound
    is TriggerException.MissingResponse -> HttpStatusCode.BadRequest
    is TriggerException.AlreadyExists -> HttpStatusCode.BadRequest
    is TriggerException.InvalidValue -> HttpStatusCode.BadRequest
    is TriggerException.MissingAudioProvider -> HttpStatusCode.BadRequest
    is TriggerAudioResponseException.NotFound -> HttpStatusCode.NotFound
    else -> HttpStatusCode.InternalServerError
}

fun Route.triggerV1() {
    authenticate(optional = environment?.testMode ?: false) {
        route("/api/v1/trigger") {
            get("/{id}") {
                finder.find(call.parameters["id"] ?: "")
                    .onLeft { return@get call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respond(it) }
            }
            get("/guild/{id}") {
                call.respond(finder.findByDiscordServer(call.parameters["id"] ?: ""))
            }
            get("/{id}/audio") {
                audioFinder.findByTriggerId(call.parameters["id"] ?: "")
                    .onLeft { return@get call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respond(it) }
            }
            post {
                triggerCreator.create(call.receive())
                    .onLeft { return@post call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respond(HttpStatusCode.Created) }
            }
            put("/{id}") {
                updater.update(TriggerUpdateRequest(id = call.parameters["id"] ?: "", call.receive()))
                    .onLeft { return@put call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respond(HttpStatusCode.OK) }
            }
            delete("/{id}") {
                deleter.delete(call.parameters["id"] ?: "")
                    .onLeft { return@delete call.respondError(errorStatusCode(it), it.message ?: "") }
                    .onRight { call.respond(HttpStatusCode.OK) }
            }
        }
    }
}
