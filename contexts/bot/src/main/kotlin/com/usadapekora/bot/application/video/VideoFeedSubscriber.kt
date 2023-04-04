package com.usadapekora.bot.application.video

import com.usadapekora.bot.domain.video.ChannelSubscriber
import kotlinx.coroutines.runBlocking

class VideoFeedSubscriber(private val subscriber: ChannelSubscriber) {

    fun subscribe() = runBlocking {
        subscriber.subscribe()
    }

}
