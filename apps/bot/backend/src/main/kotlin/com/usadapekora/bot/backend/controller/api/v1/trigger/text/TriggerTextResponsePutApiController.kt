package com.usadapekora.bot.backend.controller.api.v1.trigger.text

import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdater
import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/trigger/response/text")
class TriggerTextResponsePutApiController : ApiController() {

    val updater: TriggerTextResponseUpdater by inject(TriggerTextResponseUpdater::class.java)

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody request: TriggerTextResponseUpdateRequest.NewValues
    ): ResponseEntity<Unit> = try {
        updater.update(
            TriggerTextResponseUpdateRequest(
                id = id,
                values = request
            )
        )

        ResponseEntity.ok().build()
    } catch (exception: Exception) {
        val response = when (exception) {
            is TriggerTextResponseException.NotFound -> ResponseEntity.badRequest()
            else -> ResponseEntity.internalServerError()
        }

        response.build()
    }

}
