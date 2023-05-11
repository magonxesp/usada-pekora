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
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/v1/trigger/response/audio")
class TriggerDefaultAudioDeleteApiController : ApiController() {

    private val triggerDefaultAudioDeleter: TriggerDefaultAudioDeleter by inject(TriggerDefaultAudioDeleter::class.java)

    override fun <T> mapErrorHttpStatus(error: T): HttpStatus = when(error) {
        is TriggerAudioResponseException.NotFound -> HttpStatus.BAD_REQUEST
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String)
        = sendResultResponse(triggerDefaultAudioDeleter.delete(id))

}
