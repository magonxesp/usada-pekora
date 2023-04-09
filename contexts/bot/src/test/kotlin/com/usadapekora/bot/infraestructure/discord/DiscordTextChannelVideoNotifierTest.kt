package com.usadapekora.bot.infraestructure.discord

import com.usadapekora.bot.domain.VideoMother
import com.usadapekora.bot.infraestructure.cache.RedisKeyValueCacheStorage
import com.usadapekora.bot.testDiscordTextChannelId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class DiscordTextChannelVideoNotifierTest {

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `should send video notification`() = runTest {
        val notifier = DiscordTextChannelVideoNotifier(RedisKeyValueCacheStorage())

        val video = VideoMother.create()

        notifier.notify(video, testDiscordTextChannelId)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `should send video notification multiple times`() = runTest {
        val notifier = DiscordTextChannelVideoNotifier(RedisKeyValueCacheStorage())

        (0..100).forEach { _ ->
            val video = VideoMother.create()
            notifier.notify(video, testDiscordTextChannelId)
            delay(1000)
        }
    }

}
