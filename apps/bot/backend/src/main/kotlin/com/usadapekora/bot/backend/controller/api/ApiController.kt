package com.usadapekora.bot.backend.controller.api

import arrow.core.Either
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger

abstract class ApiController {
    protected val logger = Logger.getLogger(this::class.toString())

    abstract protected fun <T> mapErrorHttpStatus(error: T): HttpStatus

    protected fun <T> sendResultResponse(result: Either<Exception, T>, status: HttpStatus = HttpStatus.OK): ResponseEntity<T> = result.let {
        if (it.isLeft()) {
            val error = it.leftOrNull()!!
            logger.warning(error.message)
            throw ResponseStatusException(mapErrorHttpStatus(error), error.message)
        }

        return ResponseEntity.status(status).body(it.getOrNull())
    }
}
