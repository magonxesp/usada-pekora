package com.usadapekora.bot.domain.video

interface VideoFeedNotifier {
    fun notify(video: Video, target: String)
}
