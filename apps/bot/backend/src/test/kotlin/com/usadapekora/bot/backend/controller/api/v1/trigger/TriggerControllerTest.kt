package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.SpringBootHttpTestCase
import com.usadapekora.bot.application.trigger.create.TriggerAudioCreateRequest
import com.usadapekora.bot.application.trigger.create.TriggerAudioCreator
import com.usadapekora.bot.application.trigger.create.TriggerCreateRequest
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import org.koin.java.KoinJavaComponent.inject


abstract class TriggerControllerTest : SpringBootHttpTestCase()  {

    private val creator: TriggerCreator by inject(TriggerCreator::class.java)
    private val audioCreator: TriggerAudioCreator by inject(TriggerAudioCreator::class.java)

    fun createDummy(
        id: String = "29da2a75-f5ba-4bff-99ee-3eb654716284",
        title: String = "Dummy trigger",
        input: String = "peko",
        compare: String = "in",
        outputText: String? = "It's a me pekora",
        discordGuildId: String = "94101459"
    ) {
        creator.create(
            TriggerCreateRequest(
                id = id,
                title = title,
                input = input,
                compare = compare,
                discordGuildId = discordGuildId,
                responseTextId = outputText,
                responseAudioId = null,
                responseAudioProvider = null
            )
        )
    }

    fun createAudioDummy(
        id: String = "b0fcce03-7137-406c-8397-05b4d595bae9 ",
        triggerId: String = "29da2a75-f5ba-4bff-99ee-3eb654716284",
        guildId: String = "94101459"
    ) {
        audioCreator.create(
            TriggerAudioCreateRequest(
                content = readResource("/assets_audio_Its_me_pekora.mp3"),
                fileName = "assets_audio_Its_me_pekora.mp3",
                id = id,
                triggerId = triggerId,
                guildId = guildId,
            )
        )
    }
}
