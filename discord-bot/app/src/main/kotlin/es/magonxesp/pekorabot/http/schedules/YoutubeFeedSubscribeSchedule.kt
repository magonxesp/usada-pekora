package es.magonxesp.pekorabot.http.schedules

import es.magonxesp.pekorabot.modules.video.domain.VideoException
import es.magonxesp.pekorabot.modules.video.infraestructure.youtube.YoutubeFeedSubscriber
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.logging.Logger
import javax.annotation.PostConstruct

@Component
class YoutubeFeedSubscribeSchedule {

    @PostConstruct
    fun startup() = subscribe()

    @Scheduled(cron = "0 0 * * * ?")
    fun subscribeSchedule() = subscribe()

    fun subscribe(): Unit = runBlocking {
        val subscriber = YoutubeFeedSubscriber()

        try {
            subscriber.subscribe()
        } catch (exception: VideoException.FeedSubscribe) {
            Logger.getLogger(YoutubeFeedSubscribeSchedule::class.java.toString()).warning(exception.message)
        }
    }

}
