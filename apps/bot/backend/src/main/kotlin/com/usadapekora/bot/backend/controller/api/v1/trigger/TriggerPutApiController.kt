package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.application.trigger.update.TriggerUpdateRequest
import com.usadapekora.bot.application.trigger.update.TriggerUpdater
import com.usadapekora.bot.domain.trigger.TriggerException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1/trigger")
class TriggerPutApiController : ApiController() {

    val updater: TriggerUpdater by inject(TriggerUpdater::class.java)

    override fun <T> mapErrorHttpStatus(error: T) = when(error) {
        is TriggerException.NotFound -> HttpStatus.BAD_REQUEST
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: String, @RequestBody body: TriggerUpdateRequest.NewValues)
        = sendResultResponse(updater.update(TriggerUpdateRequest(id, body)))

}
