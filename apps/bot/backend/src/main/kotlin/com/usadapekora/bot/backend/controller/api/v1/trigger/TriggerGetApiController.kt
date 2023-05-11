package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.application.trigger.find.*
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFinder
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFindResponse
import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RestController
@RequestMapping("/api/v1/trigger")
class TriggerGetApiController : ApiController() {

    private val finder: TriggerFinder by inject(TriggerFinder::class.java)
    private val audioFinder: TriggerDefaultAudioFinder by inject(TriggerDefaultAudioFinder::class.java)

    override fun <T> mapErrorHttpStatus(error: T) = when(error) {
        is TriggerAudioResponseException.NotFound -> HttpStatus.NOT_FOUND
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @GetMapping("{id}")
    fun find(@PathVariable("id") id: String)
        = sendResultResponse(finder.find(id))

    @GetMapping("/guild/{id}")
    fun findByGuildId(@PathVariable("id") id: String)
        = finder.findByDiscordServer(id)

    @GetMapping("{id}/audio")
    fun findAudioByTriggerId(@PathVariable("id") id: String)
        = sendResultResponse(audioFinder.findByTriggerId(id))

}
