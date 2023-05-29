package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.HttpTestCase
import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreator
import com.usadapekora.bot.application.trigger.create.TriggerCreateRequest
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreator
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseProvider
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseContentType
import org.koin.java.KoinJavaComponent.inject


abstract class TriggerTest : HttpTestCase()  {

    private val creator: TriggerCreator by inject(TriggerCreator::class.java)
    private val audioCreator: TriggerDefaultAudioResponseCreator by inject(TriggerDefaultAudioResponseCreator::class.java)
    private val textCreator: TriggerTextResponseCreator by inject(TriggerTextResponseCreator::class.java)

    fun createDummy(
        id: String = "29da2a75-f5ba-4bff-99ee-3eb654716284",
        title: String = "Dummy trigger",
        input: String = "peko",
        compare: String = "in",
        responseTextId: String? = null,
        responseAudioId: String? = null,
        responseAudioProvider: String? = TriggerAudioResponseProvider.DEFAULT.value,
        discordGuildId: String = "94101459"
    ) {
        creator.create(
            TriggerCreateRequest(
                id = id,
                title = title,
                input = input,
                compare = compare,
                discordGuildId = discordGuildId,
                responseTextId = responseTextId,
                responseAudioId = responseAudioId,
                responseAudioProvider = responseAudioProvider
            )
        )
    }

    fun createAudioDummy(
        id: String = "b0fcce03-7137-406c-8397-05b4d595bae9",
        triggerId: String = "29da2a75-f5ba-4bff-99ee-3eb654716284",
        guildId: String = "94101459"
    ) {
        audioCreator.create(
            TriggerDefaultAudioResponseCreateRequest(
                content = readResource("/assets_audio_Its_me_pekora.mp3"),
                fileName = "assets_audio_Its_me_pekora.mp3",
                id = id,
                triggerId = triggerId,
                guildId = guildId,
            )
        )
    }

    fun createTextDummy(
        id: String = "910c4093-1e02-442e-bbd4-eaffc1aac105",
        content: String = "Konpeko Konpeko Konpeko! 3rd Generation, Usada Pekora peko! Almond...almond....!!",
        type: TriggerTextResponseContentType = TriggerTextResponseContentType.TEXT
    ) {
        textCreator.create(
            TriggerTextResponseCreateRequest(
                id = id,
                content = content,
                type = type.value
            )
        )
    }
}
