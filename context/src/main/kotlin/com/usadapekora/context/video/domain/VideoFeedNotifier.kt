package com.usadapekora.context.video.domain

interface VideoFeedNotifier {
    fun notify(video: Video, target: String)
}
