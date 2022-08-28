package es.magonxesp.pekorabot.modules.video.infraestructure.youtube

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
