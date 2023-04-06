package com.usadapekora.bot.domain.trigger.response.audio

import com.usadapekora.bot.domain.ObjectMother
import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefault
import java.util.UUID

object TriggerAudioDefaultMother : ObjectMother<TriggerAudioDefault> {
    fun create(
        id: String? = null,
        trigger: String? = null,
        guild: String? = null,
        file: String? = null,
    ): TriggerAudioDefault = TriggerAudioDefault.fromPrimitives(
        id = id ?: UUID.randomUUID().toString(),
        trigger = trigger ?: UUID.randomUUID().toString(),
        guild = guild ?: UUID.randomUUID().toString(),
        file = file ?: "${Random.instance().internet.slug()}.mp4"
    )

    override fun random(): TriggerAudioDefault = create()
}
