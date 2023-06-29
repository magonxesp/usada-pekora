package com.usadapekora.bot.backend

import com.usadapekora.bot.application.video.VideoFeedSubscriber
import com.usadapekora.shared.domain.LoggerFactory
import io.ktor.server.application.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.util.logging.Level
import java.util.logging.Logger

private val subscriber: VideoFeedSubscriber by inject(VideoFeedSubscriber::class.java)
private val loggerFactory: LoggerFactory by inject(LoggerFactory::class.java)
private val logger = loggerFactory.getLogger("com.usadapekora.bot.backend.SchedulesKt")

private suspend fun scheduleSubscribeYoutubeChannel() = coroutineScope {
    launch {
        while (true) {
            logger.info("Running the youtube channel subscribe job")

            subscriber.subscribe()
                .onLeft { logger.warning(it.message ?: "") }

            delay(24 * 60 * 60 * 1000) // 1 day delay
        }
    }
}

suspend fun Application.scheduleJobs() {
    scheduleSubscribeYoutubeChannel()
}
