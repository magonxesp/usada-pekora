package es.magonxesp.pekorabot.modules.video.application

import es.magonxesp.pekorabot.modules.video.domain.FeedParser
import es.magonxesp.pekorabot.modules.video.domain.Video

class VideoFeedParser(private val parser: FeedParser) {
    fun parse(feed: String): Video = parser.parse(feed)
}
