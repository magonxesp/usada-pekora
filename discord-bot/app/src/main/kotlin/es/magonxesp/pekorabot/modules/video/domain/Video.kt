package es.magonxesp.pekorabot.modules.video.domain

import java.time.ZonedDateTime

data class Video(
    val id: String,
    val title: String,
    val url: String,
    val publishDate: ZonedDateTime
)
