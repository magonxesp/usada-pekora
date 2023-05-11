package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.application.trigger.create.TriggerCreateRequest
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.domain.trigger.TriggerException
import io.ktor.util.reflect.*
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

    override fun <T> mapErrorHttpStatus(error: T) = when(error) {
        is TriggerException -> HttpStatus.BAD_REQUEST
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @PostMapping("")
    fun create(@RequestBody body: TriggerCreateRequest)
        = sendResultResponse(triggerCreator.create(body), HttpStatus.CREATED)

}
