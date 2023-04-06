package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.application.trigger.delete.TriggerAudioDeleter
import com.usadapekora.bot.domain.trigger.exception.TriggerAudioException
import io.ktor.util.reflect.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable


@RestController
@RequestMapping("/api/v1/trigger/audio")
class TriggerAudioDeleteApiController : ApiController() {

    private val triggerAudioDeleter: TriggerAudioDeleter by inject(TriggerAudioDeleter::class.java)

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String, ): ResponseEntity<Unit> {
        try {
            triggerAudioDeleter.delete(id)
            return ResponseEntity.status(HttpStatus.OK).build()
        } catch (exception: Exception) {
            logger.warning(exception.message)

            return when (exception) {
                is TriggerAudioException.NotFound -> ResponseEntity.badRequest().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

}
