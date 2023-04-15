package com.usadapekora.bot.backend.controller.api.v1.trigger.text

import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFindResponse
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFinder
import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/trigger/response/text")
class TriggerTextResponseGetApiController : ApiController() {

    val finder: TriggerTextResponseFinder by inject(TriggerTextResponseFinder::class.java)

    @GetMapping("{id}")
    fun findTextResponse(@PathVariable("id") id: String): ResponseEntity<TriggerTextResponseFindResponse>
        = try {
            ResponseEntity.ok(finder.find(id))
        } catch (exception: Exception) {
            val response = when (exception) {
                is TriggerTextResponseException.NotFound -> ResponseEntity.notFound()
                else -> ResponseEntity.internalServerError()
            }

            response.build()
        }

}
