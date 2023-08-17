package com.usadapekora.bot.domain.trigger.response.audio

import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.shared.domain.ObjectMother
import java.util.*

object TriggerAudioResponseMother : ObjectMother<TriggerAudioResponse> {
    fun create(
        id: String? = null,
        source: String? = null,
        sourceUri: String? = null,
    ): TriggerAudioResponse = TriggerAudioResponse.fromPrimitives(
        id = id ?: UUID.randomUUID().toString(),
        source = source ?: Random.instance().random.nextEnum(TriggerAudioResponse.TriggerAudioResponseSource::class.java).name.lowercase(),
        sourceUri = sourceUri ?: "file://${Random.instance().internet.slug()}.mp4"
    )

    override fun random(): TriggerAudioResponse = create()
}
