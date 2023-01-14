package com.usadapekora.context.application.video

import com.usadapekora.context.domain.video.Video
import com.usadapekora.context.domain.video.VideoFeedNotifier

class SendVideoFeed(private val notifier: VideoFeedNotifier) {

    fun send(video: Video, targets: Array<String>) {
        targets.forEach {
            notifier.notify(video, it)
        }
    }

}
