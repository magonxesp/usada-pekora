package es.magonxesp.pekorabot.http.controller.webhook

import es.magonxesp.pekorabot.http.HttpApplication
import kotlin.test.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.Random


@SpringBootTest(
    classes = [HttpApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class YoutubeFeedWebhookControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `should receive the feed xml`() {
        webClient.post().uri("/webhook/youtube/feed")
    }

    @Test
    fun `should return the hub challenge parameter value`() {
        val challenge = Random().nextInt().toString()

        webClient.get().uri("/webhook/youtube/feed?hub.challenge=$challenge")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(String::class.java)
            .isEqualTo(challenge)
    }

}
