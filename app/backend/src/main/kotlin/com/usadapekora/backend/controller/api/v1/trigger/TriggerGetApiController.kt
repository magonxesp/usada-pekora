package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.context.application.trigger.find.TriggerFinder
import com.usadapekora.context.application.trigger.find.TriggerResponse
import com.usadapekora.context.domain.trigger.TriggerException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@Controller
@RequestMapping("/api/v1/trigger")
class TriggerGetApiController {

    private val finder: TriggerFinder by inject(TriggerFinder::class.java)

    @GetMapping("{id}")
    fun find(@PathVariable("id") id: String): ResponseEntity<TriggerResponse> {
        return try {
            ResponseEntity.of(Optional.of(finder.find(id)))
        } catch (exception: Exception) {
            when(exception) {
                is TriggerException.NotFound -> ResponseEntity.notFound().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

}
