package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.backend.utils.MultipartFileUtils
import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreator
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioException
import io.ktor.util.reflect.*
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/v1/trigger/audio")
class TriggerAudioPostApiController : ApiController() {

    private val triggerDefaultAudioResponseCreator: TriggerDefaultAudioResponseCreator by inject(TriggerDefaultAudioResponseCreator::class.java)

    @PostMapping("", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("id") id: String,
        @RequestParam("triggerId") triggerId: String,
        @RequestParam("guildId") guildId: String,
    ): ResponseEntity<Unit> {
        try {
            triggerDefaultAudioResponseCreator.create(
                TriggerDefaultAudioResponseCreateRequest(
                id = id,
                triggerId = triggerId,
                guildId = guildId,
                fileName = MultipartFileUtils.makeTemporaryFileName(file),
                content = file.bytes
            )
            )

            return ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (exception: Exception) {
            logger.warning(exception.message)

            return when (exception) {
                is TriggerAudioException -> ResponseEntity.badRequest().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

}
