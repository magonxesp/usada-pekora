package com.usadapekora.context.application.video

import com.usadapekora.context.domain.video.ChannelSubscriber
import kotlinx.coroutines.runBlocking

class VideoFeedSubscriber(private val subscriber: ChannelSubscriber) {

    fun subscribe() = runBlocking {
        subscriber.subscribe()
    }

}
