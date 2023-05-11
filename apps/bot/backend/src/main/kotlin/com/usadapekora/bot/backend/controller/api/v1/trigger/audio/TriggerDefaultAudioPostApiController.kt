package com.usadapekora.bot.backend.controller.api.v1.trigger.audio

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.backend.utils.MultipartFileUtils
import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreator
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
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
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/v1/trigger/response/audio")
class TriggerDefaultAudioPostApiController : ApiController() {

    private val triggerDefaultAudioResponseCreator: TriggerDefaultAudioResponseCreator by inject(TriggerDefaultAudioResponseCreator::class.java)

    override fun <T> mapErrorHttpStatus(error: T) = when (error) {
        is TriggerAudioResponseException -> HttpStatus.BAD_REQUEST
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @PostMapping("", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("id") id: String,
        @RequestParam("triggerId") triggerId: String,
        @RequestParam("guildId") guildId: String,
    ): ResponseEntity<Unit> = sendResultResponse(
        triggerDefaultAudioResponseCreator.create(
            TriggerDefaultAudioResponseCreateRequest(
                id = id,
                triggerId = triggerId,
                guildId = guildId,
                fileName = file.originalFilename ?: file.name,
                content = file.bytes
            )
        ),
        HttpStatus.CREATED
    )

}
