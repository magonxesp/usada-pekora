package es.magonxesp.pekorabot.http.controller.webhook

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

@Controller
@RequestMapping("/webhook/youtube")
class YoutubeFeedWebhookController {

    @PostMapping("/feed")
    @ResponseBody
    fun postFeed(@RequestBody body: String) = ""

    @GetMapping("/feed", produces = ["text/plain; charset=UTF-8"])
    @ResponseBody
    fun hubChallenge(@RequestParam("hub.challenge") challenge: Optional<String>): String
        = if (challenge.isPresent) challenge.get() else ""

}
