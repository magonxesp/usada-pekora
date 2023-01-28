package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.controller.api.ApiController
import com.usadapekora.backend.utils.MultipartFileUtils
import com.usadapekora.context.application.trigger.create.TriggerAudioCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerAudioCreator
import com.usadapekora.context.application.trigger.delete.TriggerAudioDeleter
import com.usadapekora.context.domain.trigger.TriggerAudioException
import io.ktor.util.reflect.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable


@Controller
@RequestMapping("/api/v1/trigger/audio")
class TriggerAudioDeleteApiController : ApiController() {

    private val triggerAudioDeleter: TriggerAudioDeleter by inject(TriggerAudioDeleter::class.java)

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: String, ): ResponseEntity<Unit> {
        try {
            triggerAudioDeleter.delete(id)
            return ResponseEntity.status(HttpStatus.OK).build()
        } catch (exception: Exception) {
            logger.warning(exception.message)

            return when (exception) {
                is TriggerAudioException.NotFound -> ResponseEntity.badRequest().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

}
