package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFinder
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioResponse
import com.usadapekora.bot.application.trigger.read.TriggerDefaultAudioReader
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioException
import io.ktor.util.reflect.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*


@RestController
@RequestMapping("/api/v1/trigger/audio")
class TriggerAudioGetApiController : ApiController() {

    private val triggerAudioFinder: TriggerDefaultAudioFinder by inject(TriggerDefaultAudioFinder::class.java)
    private val triggerDefaultAudioReader: TriggerDefaultAudioReader by inject(TriggerDefaultAudioReader::class.java)

    @GetMapping("{id}")
    fun find(@PathVariable("id") id: String): ResponseEntity<TriggerDefaultAudioResponse> {
        return try {
            ResponseEntity.of(Optional.of(triggerAudioFinder.find(id)))
        } catch (exception: Exception) {
            logger.warning(exception.message)

            when (exception) {
                is TriggerAudioException.NotFound -> ResponseEntity.notFound().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }


    @GetMapping("{id}/content")
    fun content(@PathVariable("id") id: String): ResponseEntity<ByteArray> {
        return try {
            ResponseEntity.of(Optional.of(triggerDefaultAudioReader.read(id)))
        } catch (exception: Exception) {
            logger.warning(exception.message)

            when (exception) {
                is TriggerAudioException.NotFound -> ResponseEntity.notFound().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }
}
