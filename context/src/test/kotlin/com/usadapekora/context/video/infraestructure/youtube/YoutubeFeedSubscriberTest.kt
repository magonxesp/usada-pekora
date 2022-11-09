package com.usadapekora.context.video.infraestructure.youtube

import com.usadapekora.context.video.infraestructure.youtube.YoutubeFeedSubscriber
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class YoutubeFeedSubscriberTest {

    @Test
    fun `should subscribe to youtube feed`() {
        val subscriber = YoutubeFeedSubscriber()

        assertDoesNotThrow {
            runBlocking {
                subscriber.subscribe()
            }
        }
    }

}
