package com.usadapekora.bot.infraestructure.video.discord

import com.usadapekora.bot.domain.VideoMother
import com.usadapekora.shared.infrastructure.persistence.redis.RedisKeyValueRepository
import com.usadapekora.shared.testDiscordTextChannelId
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class DiscordTextChannelVideoNotifierTest {

    @Test
    fun `should send video notification`(): Unit = runBlocking {
        val notifier = DiscordTextChannelVideoNotifier(RedisKeyValueRepository())
        val video = VideoMother.create()

        notifier.notify(video, testDiscordTextChannelId)
    }

    @Test
    fun `should send video notification multiple times`(): Unit = runBlocking {
        val notifier = DiscordTextChannelVideoNotifier(RedisKeyValueRepository())

        (0..4).forEach { _ ->
            val video = VideoMother.create()
            notifier.notify(video, testDiscordTextChannelId)
            delay(500)
        }
    }

}
