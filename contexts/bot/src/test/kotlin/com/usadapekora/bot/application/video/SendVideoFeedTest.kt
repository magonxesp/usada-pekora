package com.usadapekora.bot.application.video

import com.usadapekora.bot.domain.video.VideoFeedNotifier
import com.usadapekora.bot.domain.VideoMother
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import java.util.Random
import kotlin.test.Test

class SendVideoFeedTest {

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `should send a video feed notification`() = runTest {
        val notifier = mockk<VideoFeedNotifier>(relaxed = true)
        val sender = SendVideoFeed(notifier)
        val video = VideoMother.create()
        val targets = arrayOf(Random().nextLong().toString())

        sender.send(video, targets)

        coVerify { notifier.notify(video, target = targets[0]) }
    }

}
