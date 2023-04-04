package com.usadapekora.bot.application.video

import com.usadapekora.bot.domain.video.FeedParser
import com.usadapekora.bot.domain.video.Video

class VideoFeedParser(private val parser: FeedParser) {
    fun parse(feed: String): Video = parser.parse(feed)
}
