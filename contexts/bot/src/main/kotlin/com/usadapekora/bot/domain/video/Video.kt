package com.usadapekora.bot.domain.video

import com.usadapekora.shared.domain.common.Entity
import java.time.ZonedDateTime

data class Video(
    val id: String,
    val title: String,
    val url: String,
    val publishDate: ZonedDateTime
) : Entity() {
    override fun id(): String = id
}
