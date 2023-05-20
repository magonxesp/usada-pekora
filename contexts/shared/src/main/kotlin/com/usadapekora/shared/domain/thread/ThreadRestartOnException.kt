package com.usadapekora.shared.domain.thread

import java.lang.Thread.UncaughtExceptionHandler
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.concurrent.thread

class ThreadRestartOnException(val callable: () -> Unit) : UncaughtExceptionHandler {

    private val logger = Logger.getLogger(ThreadRestartOnException::class.toString())

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (t == null) {
            return
        }

        val exceptionClass = if (e != null) e::class.toString() else ""
        logger.log(Level.WARNING, "Restarting thread ${t.name} by throwing exception $exceptionClass with message: ${e?.message}", e)

        if (t.isAlive) {
            callable()
            return
        }

        t.interrupt()

        logger.info("Waiting for thread ${t.name} interrupt")
        while (!t.isInterrupted) { continue }
        logger.info("Thread ${t.name} interrupted, starting new thread")

        thread(start = true) { callable() }.apply {
            uncaughtExceptionHandler = ThreadRestartOnException(callable)
        }
    }

}
