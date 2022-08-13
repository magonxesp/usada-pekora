package es.magonxesp.pekorabot.http.controller.webhook

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/webhook/youtube")
class YoutubeFeedWebhook {

    @PostMapping("/feed")
    fun postFeed(@RequestBody body: String) {
        println(body)
    }

    @GetMapping("/feed", produces = ["text/plain; charset=UTF-8"])
    @ResponseBody
    fun getFeed(@RequestParam("hub.challenge") challenge: String): String
        = challenge

}
