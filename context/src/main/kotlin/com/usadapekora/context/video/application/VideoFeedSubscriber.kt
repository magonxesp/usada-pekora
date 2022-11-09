package com.usadapekora.context.video.application

import com.usadapekora.context.video.domain.ChannelSubscriber
import kotlinx.coroutines.runBlocking

class VideoFeedSubscriber(private val subscriber: ChannelSubscriber) {

    fun subscribe() = runBlocking {
        subscriber.subscribe()
    }

}
