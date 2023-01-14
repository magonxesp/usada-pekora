package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.controller.api.ApiController
import com.usadapekora.backend.controller.webhook.YoutubeFeedWebhookController
import com.usadapekora.context.application.trigger.create.TriggerAudioCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerAudioCreator
import com.usadapekora.context.application.trigger.create.TriggerCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerCreator
import com.usadapekora.context.domain.trigger.TriggerAudioException
import com.usadapekora.context.domain.trigger.TriggerException
import io.ktor.util.reflect.*
import org.koin.core.component.KoinComponent
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.net.URLConnection


@Controller
@RequestMapping("/api/v1/trigger/audio")
class TriggerAudioPostApiController : ApiController() {

    private val triggerAudioCreator: TriggerAudioCreator by inject(TriggerAudioCreator::class.java)

    @PostMapping("")
    fun create(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("id") id: String,
        @RequestParam("triggerId") triggerId: String,
        @RequestParam("guildId") guildId: String,
    ): ResponseEntity<Unit> {
        try {
            val fileMimeType = URLConnection.guessContentTypeFromStream(ByteArrayInputStream(file.bytes))
                ?: return ResponseEntity.badRequest().build()

            // TODO: resolver extension porque

            triggerAudioCreator.create(TriggerAudioCreateRequest(
                id = id,
                triggerId = triggerId,
                guildId = guildId,
                fileName = file.name,
                content = file.bytes
            ))

            return ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (exception: Exception) {
            logger.warning(exception.message)

            return when (exception) {
                is TriggerAudioException -> ResponseEntity.badRequest().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

}
