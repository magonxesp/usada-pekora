package com.usadapekora.bot.domain

import com.usadapekora.shared.domain.common.DateTimeUtils
import com.usadapekora.bot.domain.video.Video
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.ZonedDateTime
import java.util.UUID

object VideoMother : ObjectMother<Video> {

    @Serializable
    data class VideoItem(val id: String, val title: String, val url: String, val publishDate: String)

    @Serializable
    data class VideoItems(val videos: Array<VideoItem>)

    private fun randomYoutubeVideo(): VideoItem {
        val resource = this::class.java.getResource("/youtube.json") ?: throw RuntimeException("test resource youtube.json not found")
        val json = resource.readBytes().decodeToString()
        val videos = Json.decodeFromString<VideoItems>(json)
        return videos.videos.random()
    }

    fun create(
        id: String? = null,
        title: String? = null,
        url: String? = null,
        publishDate: ZonedDateTime? = null
    ) = randomYoutubeVideo().let {
        Video(
            id = id ?: it.id,
            title = title ?: it.title,
            url = url ?: it.url,
            publishDate = publishDate ?: DateTimeUtils.fromISO8061(it.publishDate)
        )
    }

    override fun random(): Video = create()
}
