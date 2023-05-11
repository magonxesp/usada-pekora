package com.usadapekora.bot.backend.controller.api.v1.trigger.audio

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFinder
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFindResponse
import com.usadapekora.bot.application.trigger.read.TriggerDefaultAudioReader
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import io.ktor.util.reflect.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*


@RestController
@RequestMapping("/api/v1/trigger/response/audio")
class TriggerDefaultAudioGetApiController : ApiController() {

    private val triggerAudioFinder: TriggerDefaultAudioFinder by inject(TriggerDefaultAudioFinder::class.java)
    private val triggerDefaultAudioReader: TriggerDefaultAudioReader by inject(TriggerDefaultAudioReader::class.java)

    override fun <T> mapErrorHttpStatus(error: T): HttpStatus = when(error) {
        is TriggerAudioResponseException.NotFound -> HttpStatus.BAD_REQUEST
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @GetMapping("{id}")
    fun find(@PathVariable("id") id: String)
        = sendResultResponse(triggerAudioFinder.find(id))

    @GetMapping("{id}/content")
    fun content(@PathVariable("id") id: String)
        = sendResultResponse(triggerDefaultAudioReader.read(id))
}
