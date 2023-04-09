package com.usadapekora.bot.domain.video

interface VideoFeedNotifier {
    suspend fun notify(video: Video, target: String)
}
