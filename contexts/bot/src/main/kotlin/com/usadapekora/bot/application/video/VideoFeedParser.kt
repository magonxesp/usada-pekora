package com.usadapekora.bot.application.video

import arrow.core.Either
import com.usadapekora.bot.domain.video.FeedParser
import com.usadapekora.bot.domain.video.Video
import com.usadapekora.bot.domain.video.VideoException

class VideoFeedParser(private val parser: FeedParser) {
    fun parse(feed: String): Either<VideoException.FeedParse, Video> = parser.parse(feed)
}
