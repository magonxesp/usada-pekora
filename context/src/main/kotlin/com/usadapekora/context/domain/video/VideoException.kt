package com.usadapekora.context.domain.video

sealed class VideoException(override val message: String? = null) : Exception(message) {
    class FeedSubscribe(override val message: String? = null) : VideoException(message)
    class FeedParse(override val message: String? = null) : VideoException(message)
}
