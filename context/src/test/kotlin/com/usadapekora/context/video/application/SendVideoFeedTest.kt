package com.usadapekora.context.video.application

import com.usadapekora.context.video.domain.VideoFeedNotifier
import com.usadapekora.context.video.domain.VideoMother
import io.mockk.mockk
import io.mockk.verify
import java.util.Random
import kotlin.test.Test

class SendVideoFeedTest {

    @Test
    fun `should send a video feed notification`() {
        val notifier = mockk<VideoFeedNotifier>(relaxed = true)
        val sender = SendVideoFeed(notifier)
        val video = VideoMother.create()
        val targets = arrayOf(Random().nextLong().toString())

        sender.send(video, targets)

        verify { notifier.notify(video, target = targets[0]) }
    }

}
