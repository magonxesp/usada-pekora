package com.usadapekora.context.video.domain

import com.usadapekora.context.shared.domain.Random
import java.time.ZonedDateTime
import java.util.UUID

object VideoMother {
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
