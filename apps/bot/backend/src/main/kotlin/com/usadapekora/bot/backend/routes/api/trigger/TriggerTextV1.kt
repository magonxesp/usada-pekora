package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreator
import com.usadapekora.bot.application.trigger.delete.text.TriggerTextResponseDeleter
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFinder
import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdater
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.shared.infrastructure.common.ktor.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val triggerTextDeleter: TriggerTextResponseDeleter by inject(TriggerTextResponseDeleter::class.java)
private val triggerTextFinder: TriggerTextResponseFinder by inject(TriggerTextResponseFinder::class.java)
private val triggerTextCreator: TriggerTextResponseCreator by inject(TriggerTextResponseCreator::class.java)
private val triggerTextUpdater: TriggerTextResponseUpdater by inject(TriggerTextResponseUpdater::class.java)

private fun errorStatusCode(error: Any) = when(error) {
    is TriggerTextResponseException.AlreadyExists -> HttpStatusCode.BadRequest
    is TriggerTextResponseException.NotFound -> HttpStatusCode.NotFound
    else -> HttpStatusCode.InternalServerError
}

fun Route.triggerTextV1() {
    route("/api/v1/trigger/response/text") {
        get("/{id}") {
            triggerTextFinder.find(call.parameters["id"] ?: "")
                .onLeft { return@get call.respondError(errorStatusCode(it), it.message ?: "") }
                .onRight { call.respond(it) }
        }
        post {
            val request = call.receive<TriggerTextResponseCreateRequest>()

            triggerTextCreator.create(request)
                .onLeft { return@post call.respondError(errorStatusCode(it), it.message ?: "") }
                .onRight { call.respond(HttpStatusCode.Created) }
        }
        put("/{id}") {
            val updateRequest = call.receive<TriggerTextResponseUpdateRequest.NewValues>()
            val request = TriggerTextResponseUpdateRequest(
                id = call.parameters["id"] ?: "",
                values = updateRequest
            )

            triggerTextUpdater.update(request)
                .onLeft { return@put call.respondError(errorStatusCode(it), it.message ?: "") }
                .onRight { call.respond(HttpStatusCode.OK) }
        }
        delete("/{id}") {
            triggerTextDeleter.delete(call.parameters["id"] ?: "")
                .onLeft { return@delete call.respondError(errorStatusCode(it), it.message ?: "") }
                .onRight { call.respond(HttpStatusCode.OK) }
        }
    }
}
