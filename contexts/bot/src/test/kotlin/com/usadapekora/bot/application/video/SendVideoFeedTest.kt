package com.usadapekora.bot.application.video

import com.usadapekora.bot.domain.VideoMother
import com.usadapekora.bot.domain.video.VideoFeedNotifier
import com.usadapekora.bot.domain.video.VideoMonitoring
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import java.util.*
import kotlin.test.Test

class SendVideoFeedTest {

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `should send a video feed notification`() = runTest {
        val notifier = mockk<VideoFeedNotifier>(relaxed = true)
        val monitoring = mockk<VideoMonitoring>(relaxed = true)
        val sender = SendVideoFeed(notifier, monitoring)
        val video = VideoMother.create()
        val targets = arrayOf(Random().nextLong().toString())

        sender.send(video, targets)

        verify { monitoring.notificationProcessed() }
        verify { monitoring.notificationSentToTarget() }
        coVerify { notifier.notify(video, target = targets[0]) }
    }

}
