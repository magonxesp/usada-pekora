package com.usadapekora.bot.domain.trigger.response.audio

import com.usadapekora.bot.domain.ObjectMother
import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import java.util.UUID

object TriggerAudioDefaultMother : ObjectMother<TriggerDefaultAudioResponse> {
    fun create(
        id: String? = null,
        trigger: String? = null,
        guild: String? = null,
        file: String? = null,
    ): TriggerDefaultAudioResponse = TriggerDefaultAudioResponse.fromPrimitives(
        id = id ?: UUID.randomUUID().toString(),
        trigger = trigger ?: UUID.randomUUID().toString(),
        guild = guild ?: UUID.randomUUID().toString(),
        file = file ?: "${Random.instance().internet.slug()}.mp4"
    )

    override fun random(): TriggerDefaultAudioResponse = create()
}
