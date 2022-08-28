package es.magonxesp.pekorabot.http.schedules

import es.magonxesp.pekorabot.modules.video.application.VideoFeedSubscriber
import es.magonxesp.pekorabot.modules.video.domain.VideoException
import es.magonxesp.pekorabot.videoFeedSubscriber
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.Timer
import java.util.logging.Logger
import javax.annotation.PostConstruct
import kotlin.concurrent.schedule

@Component
class YoutubeFeedSubscribeSchedule {

    @PostConstruct
    fun subscribePostConstruct() = Timer().schedule(5000) {
        try {
            videoFeedSubscriber().subscribe()
        } catch (exception: VideoException.FeedSubscribe) {
            Logger.getLogger(YoutubeFeedSubscribeSchedule::class.toString()).warning(exception.message)
        }
    }

    @Scheduled(cron = "0 0 * * * ?")
    fun subscribeSchedule() {
        try {
            videoFeedSubscriber().subscribe()
        } catch (exception: VideoException.FeedSubscribe) {
            Logger.getLogger(YoutubeFeedSubscribeSchedule::class.toString()).warning(exception.message)
        }
    }

}
