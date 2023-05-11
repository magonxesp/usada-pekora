package com.usadapekora.bot.backend.controller.api.v1.trigger.text

import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdater
import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/trigger/response/text")
class TriggerTextResponsePutApiController : ApiController() {

    val updater: TriggerTextResponseUpdater by inject(TriggerTextResponseUpdater::class.java)

    override fun <T> mapErrorHttpStatus(error: T) = when(error) {
        is TriggerTextResponseException.NotFound -> HttpStatus.BAD_REQUEST
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody request: TriggerTextResponseUpdateRequest.NewValues
    ) = sendResultResponse(
        updater.update(
            TriggerTextResponseUpdateRequest(
                id = id,
                values = request
            )
        )
    )

}
