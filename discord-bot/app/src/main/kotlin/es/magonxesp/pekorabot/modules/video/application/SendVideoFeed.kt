package es.magonxesp.pekorabot.modules.video.application

import es.magonxesp.pekorabot.modules.video.domain.Video
import es.magonxesp.pekorabot.modules.video.domain.VideoFeedNotifier

class SendVideoFeed(private val notifier: VideoFeedNotifier) {

    fun send(video: Video, targets: Array<String>) {
        targets.forEach {
            notifier.notify(video, it)
        }
    }

}
