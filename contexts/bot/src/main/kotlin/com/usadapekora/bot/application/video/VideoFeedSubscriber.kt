package com.usadapekora.bot.application.video

import arrow.core.Either
import com.usadapekora.bot.domain.video.ChannelSubscriber
import com.usadapekora.bot.domain.video.VideoException
import kotlinx.coroutines.runBlocking

class VideoFeedSubscriber(private val subscriber: ChannelSubscriber) {

    fun subscribe(): Either<VideoException.FeedSubscribe, Unit> = runBlocking {
        subscriber.subscribe()
    }

}
