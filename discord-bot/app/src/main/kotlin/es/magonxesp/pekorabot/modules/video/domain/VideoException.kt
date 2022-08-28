package es.magonxesp.pekorabot.modules.video.domain

sealed class VideoException(override val message: String?) : Exception(message) {
    class FeedSubscribe(override val message: String?) : VideoException(message)
}
