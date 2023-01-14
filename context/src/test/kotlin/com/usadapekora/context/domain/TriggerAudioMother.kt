package com.usadapekora.context.domain

import com.usadapekora.context.domain.trigger.TriggerAudio
import java.util.UUID

object TriggerAudioMother : ObjectMother<TriggerAudio> {
    fun create(
        id: String? = null,
        trigger: String? = null,
        guild: String? = null,
        name: String? = null,
    ): TriggerAudio = TriggerAudio.fromPrimitives(
        id = id ?: UUID.randomUUID().toString(),
        trigger = trigger ?: UUID.randomUUID().toString(),
        guild = guild ?: UUID.randomUUID().toString(),
        name = name ?: "${Random.instance().internet.slug()}.${Random.instance().file.extension()}"
    )

    override fun random(): TriggerAudio = create()
}
