package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.controller.api.ApiController
import com.usadapekora.bot.application.trigger.delete.TriggerDeleter
import com.usadapekora.bot.domain.trigger.TriggerException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1/trigger")
class TriggerDeleteApiController : ApiController() {

    val deleter: TriggerDeleter by inject(TriggerDeleter::class.java)

    override fun <T> mapErrorHttpStatus(error: T) = when(error) {
        is TriggerException.NotFound -> HttpStatus.BAD_REQUEST
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String)
        = sendResultResponse(deleter.delete(id))

}
