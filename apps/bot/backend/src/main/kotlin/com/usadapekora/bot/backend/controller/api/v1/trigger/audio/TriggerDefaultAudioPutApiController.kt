package com.usadapekora.bot.backend.controller.api.v1.trigger.audio

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.application.trigger.update.audio.TriggerDefaultAudioResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.audio.TriggerDefaultAudioResponseUpdater
import com.usadapekora.bot.backend.utils.MultipartFileUtils
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import io.ktor.util.reflect.*
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/api/v1/trigger/response/audio")
class TriggerDefaultAudioPutApiController : ApiController() {

    private val triggerDefaultAudioResponseUpdater: TriggerDefaultAudioResponseUpdater by inject(TriggerDefaultAudioResponseUpdater::class.java)

    private fun mapHttpResponseCodeError(exception: Exception) = when (exception) {
        is TriggerAudioResponseException -> HttpStatus.BAD_REQUEST
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestParam("file") file: Optional<MultipartFile>,
        @RequestParam("triggerId") triggerId: Optional<String>,
        @RequestParam("guildId") guildId: Optional<String>
    ): ResponseEntity<Unit> {
        try {
            triggerDefaultAudioResponseUpdater.update(
                TriggerDefaultAudioResponseUpdateRequest(
                    id = id,
                    values = TriggerDefaultAudioResponseUpdateRequest.NewValues(
                        fileName = file.map { it.originalFilename ?: it.name }.orElse(null),
                        triggerId = triggerId.orElse(null),
                        guildId = guildId.orElse(null),
                        content = file.map { it.bytes }.orElse(null)
                    )
                )
            )

            return ResponseEntity.status(HttpStatus.OK).build()
        } catch (exception: Exception) {
            logger.warning(exception.message)
            throw ResponseStatusException(mapHttpResponseCodeError(exception), exception.message, exception)
        }
    }

}
