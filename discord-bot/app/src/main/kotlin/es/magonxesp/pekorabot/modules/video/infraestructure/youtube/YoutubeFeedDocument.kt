package es.magonxesp.pekorabot.modules.video.infraestructure.youtube

import es.magonxesp.pekorabot.modules.shared.domain.DateTimeUtils
import es.magonxesp.pekorabot.modules.video.domain.Video
import org.w3c.dom.Document
import org.w3c.dom.Element

class YoutubeFeedDocument(private val document: Document) {

    private val namespace = "http://www.youtube.com/xml/schemas/2015"
    private val entry: Element = document.getElementsByTagName("entry").item(0) as Element

    val id: String = entry.getElementsByTagNameNS(namespace, "videoId").item(0).textContent
    val title: String = entry.getElementsByTagName("title").item(0).textContent
    val url: String = entry.getElementsByTagName("link").item(0).attributes.getNamedItem("href").textContent
    val publishDate: String = entry.getElementsByTagName("published").item(0).textContent

    fun toAggregate() = Video(
        id = id,
        title = title,
        url = url,
        publishDate = DateTimeUtils.fromISO8061(publishDate)
    )
}
