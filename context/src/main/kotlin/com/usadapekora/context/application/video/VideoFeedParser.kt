package com.usadapekora.context.application.video

import com.usadapekora.context.domain.video.FeedParser
import com.usadapekora.context.domain.video.Video

class VideoFeedParser(private val parser: FeedParser) {
    fun parse(feed: String): Video = parser.parse(feed)
}
