package com.usadapekora.context.infraestructure.youtube

import com.usadapekora.context.domain.video.FeedParser
import com.usadapekora.context.domain.video.Video
import com.usadapekora.context.domain.video.VideoException
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class YoutubeVideoParser: FeedParser {

    override fun parse(feed: String): Video {
        try {
            val document = DocumentBuilderFactory.newInstance()
                .apply {
                    isValidating = false
                    isIgnoringElementContentWhitespace = true
                    isNamespaceAware = true
                }
                .newDocumentBuilder()
                .parse(InputSource(StringReader(feed)))

            return YoutubeFeedDocument(document).toAggregate()
        } catch (exception: SAXException) {
            throw VideoException.FeedParse(exception.message)
        }
    }

}
