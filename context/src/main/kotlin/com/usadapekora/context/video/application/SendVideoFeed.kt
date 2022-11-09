package com.usadapekora.context.video.application

import com.usadapekora.context.video.domain.Video
import com.usadapekora.context.video.domain.VideoFeedNotifier

class SendVideoFeed(private val notifier: VideoFeedNotifier) {

    fun send(video: Video, targets: Array<String>) {
        targets.forEach {
            notifier.notify(video, it)
        }
    }

}
