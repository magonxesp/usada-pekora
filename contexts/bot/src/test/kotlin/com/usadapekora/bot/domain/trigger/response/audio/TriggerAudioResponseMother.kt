package com.usadapekora.bot.domain.trigger.response.audio

import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.shared.domain.ObjectMother
import java.util.*

object TriggerAudioResponseMother : ObjectMother<TriggerAudioResponse> {
    fun create(
        id: String? = null,
        trigger: String? = null,
        guild: String? = null,
        file: String? = null,
    ): TriggerAudioResponse = TriggerAudioResponse.fromPrimitives(
        id = id ?: UUID.randomUUID().toString(),
        trigger = trigger ?: UUID.randomUUID().toString(),
        guild = guild ?: UUID.randomUUID().toString(),
        sourceUri = file ?: "${Random.instance().internet.slug()}.mp4"
    )

    override fun random(): TriggerAudioResponse = create()
}
