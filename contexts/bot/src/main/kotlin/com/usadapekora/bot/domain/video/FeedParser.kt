package com.usadapekora.bot.domain.video

import arrow.core.Either

interface FeedParser {
    fun parse(feed: String): Either<VideoException.FeedParse, Video>
}
