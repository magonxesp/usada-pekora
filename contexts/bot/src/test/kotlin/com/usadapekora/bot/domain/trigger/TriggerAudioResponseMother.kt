package com.usadapekora.bot.domain.trigger

import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.shared.domain.ObjectMother
import java.util.*

object TriggerAudioResponseMother : ObjectMother<TriggerAudioResponse> {
    fun create(
        id: String? = null,
        kind: String? = null,
        source: String? = null,
    ): TriggerAudioResponse = TriggerAudioResponse.fromPrimitives(
        id = id ?: UUID.randomUUID().toString(),
        kind = kind ?: Random.instance().random.nextEnum(TriggerAudioResponse.TriggerAudioResponseKind::class.java).name.lowercase(),
        source = source ?: "${Random.instance().internet.slug()}.mp4"
    )

    override fun random(): TriggerAudioResponse = create()
}
