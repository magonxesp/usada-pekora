package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.application.trigger.find.*
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFinder
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFindResponse
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RestController
@RequestMapping("/api/v1/trigger")
class TriggerGetApiController {

    private val finder: TriggerFinder by inject(TriggerFinder::class.java)
    private val audioFinder: TriggerDefaultAudioFinder by inject(TriggerDefaultAudioFinder::class.java)

    @GetMapping("{id}")
    fun find(@PathVariable("id") id: String): ResponseEntity<TriggerResponse> {
        return try {
            ResponseEntity.of(Optional.of(finder.find(id).getOrNull()!!))
        } catch (exception: Exception) {
            when(exception) {
                is TriggerException.NotFound -> ResponseEntity.notFound().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

    @GetMapping("/guild/{id}")
    fun findByGuildId(@PathVariable("id") id: String): ResponseEntity<TriggersResponse> {
        return try {
            ResponseEntity.of(Optional.of(finder.findByDiscordServer(id)))
        } catch (exception: Exception) {
            when(exception) {
                is TriggerException.NotFound -> ResponseEntity.notFound().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

    @GetMapping("{id}/audio")
    fun findAudioByTriggerId(@PathVariable("id") id: String): ResponseEntity<TriggerDefaultAudioFindResponse> {
        return try {
            ResponseEntity.of(Optional.of(audioFinder.findByTriggerId(id)))
        } catch (exception: Exception) {
            when(exception) {
                is TriggerAudioResponseException.NotFound -> ResponseEntity.notFound().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

}
