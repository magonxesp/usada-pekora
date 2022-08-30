package es.magonxesp.pekorabot.modules.video.domain

interface VideoFeedNotifier {
    fun notify(video: Video, target: String)
}
