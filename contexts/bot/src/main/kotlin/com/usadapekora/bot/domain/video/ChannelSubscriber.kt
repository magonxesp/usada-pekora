package com.usadapekora.bot.domain.video

import arrow.core.Either

interface ChannelSubscriber {
    suspend fun subscribe(): Either<VideoException.FeedSubscribe, Unit>
}
