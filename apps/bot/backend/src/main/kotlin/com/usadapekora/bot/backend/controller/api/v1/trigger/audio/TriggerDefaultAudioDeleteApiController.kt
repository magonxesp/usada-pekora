package com.usadapekora.bot.backend.controller.api.v1.trigger.audio

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.application.trigger.delete.audio.TriggerDefaultAudioDeleter
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import io.ktor.util.reflect.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable


@RestController
@RequestMapping("/api/v1/trigger/response/audio")
class TriggerDefaultAudioDeleteApiController : ApiController() {

    private val triggerDefaultAudioDeleter: TriggerDefaultAudioDeleter by inject(TriggerDefaultAudioDeleter::class.java)

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String, ): ResponseEntity<Unit> {
        try {
            triggerDefaultAudioDeleter.delete(id)
            return ResponseEntity.status(HttpStatus.OK).build()
        } catch (exception: Exception) {
            logger.warning(exception.message)

            return when (exception) {
                is TriggerAudioResponseException.NotFound -> ResponseEntity.badRequest().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

}
