package es.magonxesp.pekorabot.modules.video.application

import es.magonxesp.pekorabot.modules.video.domain.ChannelSubscriber
import es.magonxesp.pekorabot.modules.video.domain.VideoException
import kotlinx.coroutines.runBlocking
import java.util.logging.Logger

class VideoFeedSubscriber(private val subscriber: ChannelSubscriber) {

    fun subscribe() = runBlocking{
        try {
            subscriber.subscribe()
        } catch (exception: VideoException.FeedSubscribe) {
            Logger.getLogger(VideoFeedSubscriber::class.toString()).warning(exception.message)
        }
    }

}
