package com.usadapekora.bot.application.video

import com.usadapekora.bot.domain.video.Video
import com.usadapekora.bot.domain.video.VideoFeedNotifier

class SendVideoFeed(private val notifier: VideoFeedNotifier) {

    suspend fun send(video: Video, targets: Array<String>) {
        targets.forEach {
            notifier.notify(video, it)
        }
    }

}
