package es.magonxesp.pekorabot.modules.video.domain

interface FeedParser {
    /**
     * Parse the feed content to Video aggregate
     *
     * @throws VideoException.FeedParse in case of parse failure
     */
    fun parse(feed: String): Video
}
