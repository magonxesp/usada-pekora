package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.application.trigger.delete.TriggerDeleter
import com.usadapekora.bot.domain.trigger.exception.TriggerException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1/trigger")
class TriggerDeleteApiController : ApiController() {

    val deleter: TriggerDeleter by inject(TriggerDeleter::class.java)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> {
        return try {
            deleter.delete(id)
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
