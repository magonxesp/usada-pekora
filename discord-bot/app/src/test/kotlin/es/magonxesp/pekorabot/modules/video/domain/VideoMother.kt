package es.magonxesp.pekorabot.modules.video.domain

import es.magonxesp.pekorabot.modules.shared.domain.Random
import java.time.ZonedDateTime
import java.util.UUID

class VideoMother {
    companion object {
        fun create(
            id: String? = null,
            title: String? = null,
            url: String? = null,
            publishDate: ZonedDateTime? = null
        ) = Video(
            id = id ?: UUID.randomUUID().toString(),
            title = title ?: Random.instance().lorem.words(),
            url = url ?: Random.instance().internet.domain(),
            publishDate = publishDate ?: ZonedDateTime.now()
        )
    }
}
