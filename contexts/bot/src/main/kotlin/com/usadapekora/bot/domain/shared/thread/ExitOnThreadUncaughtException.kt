package com.usadapekora.bot.domain.shared.thread

import java.lang.Thread.UncaughtExceptionHandler
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.system.exitProcess

class ExitOnThreadUncaughtException : UncaughtExceptionHandler {

    private val logger = Logger.getLogger(ExitOnThreadUncaughtException::class.toString())

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (t == null || e == null) {
            return
        }

        logger.log(Level.WARNING, "Uncaught exception ${e::class} on thread ${t.name} with message: ${e.message}", e)
        logger.info("Exiting of the jvm")
        exitProcess(1)
    }
}
