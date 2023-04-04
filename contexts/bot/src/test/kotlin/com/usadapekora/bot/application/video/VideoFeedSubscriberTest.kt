package com.usadapekora.bot.application.video

import com.usadapekora.bot.domain.video.ChannelSubscriber
import com.usadapekora.bot.domain.video.VideoException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class VideoFeedSubscriberTest {

    @Test
    fun `should subscribe`() {
        val subscriber = mockk<ChannelSubscriber>(relaxed = true)
        val feedSubscriber = VideoFeedSubscriber(subscriber)

        feedSubscriber.subscribe()

        coVerify(exactly = 1) { subscriber.subscribe() }
    }

    @Test
    fun `should fail on subscribe`() {
        val subscriber = mockk<ChannelSubscriber>(relaxed = true)
        val feedSubscriber = VideoFeedSubscriber(subscriber)

        coEvery { subscriber.subscribe() } throws VideoException.FeedSubscribe()

        assertThrows<VideoException.FeedSubscribe> {
            feedSubscriber.subscribe()
        }

        coVerify(exactly = 1) { subscriber.subscribe() }
    }

}
