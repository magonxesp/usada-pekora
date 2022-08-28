package es.magonxesp.pekorabot.http.schedules

import es.magonxesp.pekorabot.videoFeedSubscriber
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.Timer
import javax.annotation.PostConstruct
import kotlin.concurrent.schedule

@Component
class YoutubeFeedSubscribeSchedule {

    @PostConstruct
    fun subscribePostConstruct() = Timer().schedule(5000) {
        videoFeedSubscriber().subscribe()
    }

    @Scheduled(cron = "0 0 * * * ?")
    fun subscribeSchedule() = videoFeedSubscriber().subscribe()

}
