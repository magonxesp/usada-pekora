package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.controller.api.ApiController
import com.usadapekora.context.application.trigger.find.TriggerAudioFinder
import com.usadapekora.context.application.trigger.find.TriggerAudioResponse
import com.usadapekora.context.domain.trigger.TriggerAudioException
import io.ktor.util.reflect.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*


@Controller
@RequestMapping("/api/v1/trigger/audio")
class TriggerAudioGetApiController : ApiController() {

    private val triggerAudiFinder: TriggerAudioFinder by inject(TriggerAudioFinder::class.java)

    @GetMapping("{id}")
    fun find(@PathVariable("id") id: String): ResponseEntity<TriggerAudioResponse> {
        return try {
            ResponseEntity.of(Optional.of(triggerAudiFinder.find(id)))
        } catch (exception: Exception) {
            logger.warning(exception.message)

            when (exception) {
                is TriggerAudioException.NotFound -> ResponseEntity.notFound().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

}
