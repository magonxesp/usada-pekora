package com.usadapekora.bot.domain

import com.usadapekora.bot.domain.video.Video
import java.time.ZonedDateTime
import java.util.UUID

object VideoMother : ObjectMother<Video> {
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

    override fun random(): Video = create()
}
