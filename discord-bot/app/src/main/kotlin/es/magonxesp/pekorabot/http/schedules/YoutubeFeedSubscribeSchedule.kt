package es.magonxesp.pekorabot.http.schedules

import es.magonxesp.pekorabot.modules.video.application.VideoFeedSubscriber
import es.magonxesp.pekorabot.modules.video.domain.VideoException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.Timer
import java.util.logging.Logger
import javax.annotation.PostConstruct
import kotlin.concurrent.schedule

@Component
class YoutubeFeedSubscribeSchedule {

    private val logger = Logger.getLogger(YoutubeFeedSubscribeSchedule::class.toString())
    private val subscriber: VideoFeedSubscriber by inject(VideoFeedSubscriber::class.java)

    @PostConstruct
    fun subscribePostConstruct() = Timer().schedule(5000) {
        try {
            subscriber.subscribe()
        } catch (exception: VideoException.FeedSubscribe) {
            logger.warning(exception.message)
        }
    }

    @Scheduled(cron = "0 0 * * * ?")
    fun subscribeSchedule() {
        try {
            subscriber.subscribe()
        } catch (exception: VideoException.FeedSubscribe) {
            logger.warning(exception.message)
        }
    }

}
