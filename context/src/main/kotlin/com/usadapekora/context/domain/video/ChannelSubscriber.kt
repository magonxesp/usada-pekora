package com.usadapekora.context.domain.video

interface ChannelSubscriber {
    /**
     * Subscribe to video streaming channel feed
     *
     * @throws VideoException.FeedSubscribe in case is unable to subscribe to feed
     */
    suspend fun subscribe()
}
