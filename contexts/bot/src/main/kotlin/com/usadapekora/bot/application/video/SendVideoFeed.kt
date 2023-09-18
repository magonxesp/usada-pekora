package com.usadapekora.bot.application.video

import com.usadapekora.bot.domain.video.Video
import com.usadapekora.bot.domain.video.VideoFeedNotifier
import com.usadapekora.bot.domain.video.VideoMonitoring

class SendVideoFeed(private val notifier: VideoFeedNotifier, private val videoMonitoring: VideoMonitoring) {

    suspend fun send(video: Video, targets: Array<String>) {
        targets.forEach {
            notifier.notify(video, it)
            videoMonitoring.notificationSentToTarget()
        }

        videoMonitoring.notificationProcessed()
    }

}
