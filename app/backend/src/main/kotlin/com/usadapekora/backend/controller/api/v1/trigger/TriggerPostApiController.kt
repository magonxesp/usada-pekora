package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.controller.api.ApiController
import com.usadapekora.backend.controller.webhook.YoutubeFeedWebhookController
import com.usadapekora.context.application.trigger.create.TriggerCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerCreator
import com.usadapekora.context.domain.trigger.TriggerException
import io.ktor.util.reflect.*
import org.koin.core.component.KoinComponent
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/api/v1/trigger")
class TriggerPostApiController : ApiController() {

    private val triggerCreator: TriggerCreator by inject(TriggerCreator::class.java)

    @PostMapping("")
    fun create(@RequestBody body: TriggerCreateRequest): ResponseEntity<Unit> {
        return try {
            triggerCreator.create(body)
            ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (exception: Exception) {
            logger.warning(exception.message)

            when(exception) {
                is TriggerException -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
                else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }
        }
    }

}
