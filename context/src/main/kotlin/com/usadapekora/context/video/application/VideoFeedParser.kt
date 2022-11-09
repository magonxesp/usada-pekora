package com.usadapekora.context.video.application

import com.usadapekora.context.video.domain.FeedParser
import com.usadapekora.context.video.domain.Video

class VideoFeedParser(private val parser: FeedParser) {
    fun parse(feed: String): Video = parser.parse(feed)
}
