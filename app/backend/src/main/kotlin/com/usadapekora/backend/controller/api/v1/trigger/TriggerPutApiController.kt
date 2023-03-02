package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.controller.api.ApiController
import com.usadapekora.context.application.trigger.update.TriggerUpdateRequest
import com.usadapekora.context.application.trigger.update.TriggerUpdater
import com.usadapekora.context.domain.trigger.TriggerException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1/trigger")
class TriggerPutApiController : ApiController() {

    val updater: TriggerUpdater by inject(TriggerUpdater::class.java)

    @PutMapping("{id}")
    fun update(@PathVariable id: String, @RequestBody body: TriggerUpdateRequest.NewValues): ResponseEntity<Unit> {
        return try {
            updater.update(TriggerUpdateRequest(id, body))
            ResponseEntity.ok().build()
        } catch (excepton: Exception) {
            logger.warning(excepton.message)

            when(excepton) {
                is TriggerException.NotFound -> ResponseEntity.badRequest().build()
                else -> ResponseEntity.internalServerError().build()
            }
        }
    }

}
