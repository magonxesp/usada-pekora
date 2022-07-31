package es.magonxesp.pekorabot.modules.video.infraestructure.youtube

import javax.xml.parsers.DocumentBuilderFactory

class YoutubeVideoParser {

    fun parse(xml: String) {
        val document = DocumentBuilderFactory.newDefaultInstance()
            .apply {
                isValidating = true
                isIgnoringElementContentWhitespace = true
            }
            .newDocumentBuilder()
            .parse("")

        // document.
    }

}
