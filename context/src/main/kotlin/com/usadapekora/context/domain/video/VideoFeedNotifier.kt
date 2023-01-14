package com.usadapekora.context.domain.video

interface VideoFeedNotifier {
    fun notify(video: Video, target: String)
}
