package es.magonxesp.pekorabot.modules.video.domain

interface ChannelSubscriber {
    /**
     * Subscribe to video streaming channel feed
     *
     * @throws VideoException.FeedSubscribe in case is unable to subscribe to feed
     */
    suspend fun subscribe()
}
