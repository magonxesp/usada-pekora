package com.usadapekora.bot.backend

import com.usadapekora.bot.application.video.VideoFeedSubscriber
import io.ktor.server.application.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.util.logging.Level
import java.util.logging.Logger

private val logger = Logger.getLogger("Schedules")
private val subscriber: VideoFeedSubscriber by inject(VideoFeedSubscriber::class.java)

private suspend fun scheduleSubscribeYoutubeChannel() = coroutineScope {
    launch {
        while (true) {
            logger.log(Level.INFO, "Running the youtube channel subscribe job")

            subscriber.subscribe()
                .onLeft { logger.log(Level.WARNING, it.message) }

            delay(24 * 60 * 60 * 1000) // 1 day delay
        }
    }
}

suspend fun Application.scheduleJobs() {
    scheduleSubscribeYoutubeChannel()
}
