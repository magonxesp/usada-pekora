package es.magonxesp.pekorabot.modules.shared.domain.thread

import java.lang.Thread.UncaughtExceptionHandler
import java.util.logging.Logger

class ThreadRestartOnException : UncaughtExceptionHandler {

    private val logger = Logger.getLogger(ThreadRestartOnException::class.toString())

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        t?.apply {
            val exceptionClass = if (e != null) e::class.toString() else ""
            logger.warning("Restarting thread $name by throwing exception $exceptionClass with message: ${e?.message}")

            if (isAlive) {
                logger.info("Waiting for thread $name interrupt")
                interrupt()
            }

            while (!isInterrupted) { continue }

            logger.info("Thread $name interrupted, attempting to restart the thread")

            start()
        }
    }

}
