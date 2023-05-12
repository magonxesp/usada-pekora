package com.usadapekora.bot.infraestructure.youtube

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.video.FeedParser
import com.usadapekora.bot.domain.video.Video
import com.usadapekora.bot.domain.video.VideoException
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class YoutubeVideoParser: FeedParser {

    override fun parse(feed: String): Either<VideoException.FeedParse, Video> {
        return try {
            val document = DocumentBuilderFactory.newInstance()
                .apply {
                    isValidating = false
                    isIgnoringElementContentWhitespace = true
                    isNamespaceAware = true
                }
                .newDocumentBuilder()
                .parse(InputSource(StringReader(feed)))

            YoutubeFeedDocument(document).toAggregate().right()
        } catch (exception: SAXException) {
            VideoException.FeedParse(exception.message).left()
        }
    }

}
