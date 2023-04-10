package com.usadapekora.bot.infraestructure.discord

import com.usadapekora.bot.domain.VideoMother
import com.usadapekora.bot.infraestructure.cache.RedisKeyValueCacheStorage
import com.usadapekora.bot.testDiscordTextChannelId
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class DiscordTextChannelVideoNotifierTest {

    @Test
    fun `should send video notification`(): Unit = runBlocking {
        val notifier = DiscordTextChannelVideoNotifier(RedisKeyValueCacheStorage())
        val video = VideoMother.create()

        notifier.notify(video, testDiscordTextChannelId)
    }

    @Test
    fun `should send video notification multiple times`(): Unit = runBlocking {
        val notifier = DiscordTextChannelVideoNotifier(RedisKeyValueCacheStorage())

        (0..4).forEach { _ ->
            val video = VideoMother.create()
            notifier.notify(video, testDiscordTextChannelId)
            delay(500)
        }
    }

}
