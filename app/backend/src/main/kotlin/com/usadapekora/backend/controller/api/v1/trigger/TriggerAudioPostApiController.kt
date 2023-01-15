package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.controller.api.ApiController
import com.usadapekora.backend.utils.MultipartFileUtils
import com.usadapekora.context.application.trigger.create.TriggerAudioCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerAudioCreator
import com.usadapekora.context.domain.trigger.TriggerAudioException
import io.ktor.util.reflect.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile


@Controller
@RequestMapping("/api/v1/trigger/audio")
class TriggerAudioPostApiController : ApiController() {

    private val triggerAudioCreator: TriggerAudioCreator by inject(TriggerAudioCreator::class.java)

    @PostMapping("", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("id") id: String,
        @RequestParam("triggerId") triggerId: String,
        @RequestParam("guildId") guildId: String,
    ): ResponseEntity<Unit> {
        try {
            triggerAudioCreator.create(TriggerAudioCreateRequest(
                id = id,
                triggerId = triggerId,
                guildId = guildId,
                fileName = MultipartFileUtils.makeTemporaryFileName(file),
                content = file.bytes
            ))

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
