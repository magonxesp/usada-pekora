package com.usadapekora.context.shared.infraestructure.logger

import com.usadapekora.context.shared.domain.Logger
import org.slf4j.LoggerFactory

class Sfl4jLogger : Logger {
    override fun warning(logger: String, message: String, exception: Exception) {
        LoggerFactory.getLogger(logger).warn(message, exception)
    }

    override fun warning(logger: String, message: String) {
        LoggerFactory.getLogger(logger).warn(message)
    }

    override fun info(logger: String, message: String, exception: Exception) {
        LoggerFactory.getLogger(logger).info(message, exception)
    }

    override fun info(logger: String, message: String) {
        LoggerFactory.getLogger(logger).info(message)
    }

    override fun error(logger: String, message: String, exception: Exception) {
        LoggerFactory.getLogger(logger).error(message, exception)
    }

    override fun error(logger: String, message: String) {
        LoggerFactory.getLogger(logger).error(message)
    }
}
