package com.usadapekora.bot.domain

import com.usadapekora.bot.domain.trigger.TriggerAudio
import java.util.UUID

object TriggerAudioMother : ObjectMother<TriggerAudio> {
    fun create(
        id: String? = null,
        trigger: String? = null,
        guild: String? = null,
        file: String? = null,
    ): TriggerAudio = TriggerAudio.fromPrimitives(
        id = id ?: UUID.randomUUID().toString(),
        trigger = trigger ?: UUID.randomUUID().toString(),
        guild = guild ?: UUID.randomUUID().toString(),
        file = file ?: "${Random.instance().internet.slug()}.mp4"
    )

    override fun random(): TriggerAudio = create()
}
