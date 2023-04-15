package com.usadapekora.bot.backend.controller.api.v1.trigger.text

import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreator
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFindResponse
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFinder
import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/trigger/response/text")
class TriggerTextResponsePostApiController : ApiController() {

    val creator: TriggerTextResponseCreator by inject(TriggerTextResponseCreator::class.java)

    @PostMapping("")
    fun create(@RequestBody request: TriggerTextResponseCreateRequest): ResponseEntity<Unit>
        = try {
            creator.create(request)
            ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (exception: Exception) {
            val response = when (exception) {
                is TriggerTextResponseException.AlreadyExists -> ResponseEntity.badRequest()
                else -> ResponseEntity.internalServerError()
            }

            response.build()
        }

}
