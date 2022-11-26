package com.usadapekora.context.trigger.domain

import com.usadapekora.context.shared.domain.Random
import java.util.UUID

object TriggerAudioMother {
    fun create(
        id: String? = null,
        trigger: String? = null,
        name: String? = null,
        path: String? = null
    ): TriggerAudio = TriggerAudio.fromPrimitives(
        id = id ?: UUID.randomUUID().toString(),
        trigger = trigger ?: UUID.randomUUID().toString(),
        name = name ?: Random.instance().chiquito.expressions(),
        path = path ?: "${Random.instance().internet.slug()}.${Random.instance().file.extension()}"
    )
}
