package es.magonxesp.pekorabot.modules.video.infraestructure.youtube

import es.magonxesp.pekorabot.modules.video.domain.FeedParser
import es.magonxesp.pekorabot.modules.video.domain.Video
import es.magonxesp.pekorabot.modules.video.domain.VideoException
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
