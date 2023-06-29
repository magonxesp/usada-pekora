package com.usadapekora.shared.infrastructure

import com.usadapekora.shared.domain.Logger
import org.slf4j.Logger as Slf4jLibLogger

class Slf4jLogger(private val logger: Slf4jLibLogger) : Logger {
    override fun warning(message: String, exception: Exception) {
        logger.warn(message, exception)
    }

    override fun warning(message: String) {
        logger.warn(message)
    }

    override fun info(message: String, exception: Exception) {
        logger.info(message, exception)
    }

    override fun info(message: String) {
        logger.info(message)
    }

    override fun error(message: String, exception: Exception) {
        logger.error(message, exception)
    }

    override fun error(message: String) {
        logger.error(message)
    }
}
