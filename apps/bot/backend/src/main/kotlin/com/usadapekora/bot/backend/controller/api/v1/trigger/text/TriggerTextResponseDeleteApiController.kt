package com.usadapekora.bot.backend.controller.api.v1.trigger.text

import com.usadapekora.bot.application.trigger.delete.text.TriggerTextResponseDeleter
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFindResponse
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFinder
import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/trigger/response/text")
class TriggerTextResponseDeleteApiController : ApiController() {

    val deleter: TriggerTextResponseDeleter by inject(TriggerTextResponseDeleter::class.java)

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Unit>
        = try {
            ResponseEntity.ok(deleter.delete(id))
        } catch (exception: Exception) {
            val response = when (exception) {
                is TriggerTextResponseException.NotFound -> ResponseEntity.badRequest()
                else -> ResponseEntity.internalServerError()
            }

            response.build()
        }

}
