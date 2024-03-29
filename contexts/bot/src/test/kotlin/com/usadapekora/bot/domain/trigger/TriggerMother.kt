package com.usadapekora.bot.domain.trigger

import com.usadapekora.bot.domain.Random
import com.usadapekora.shared.domain.ObjectMother

object TriggerMother : ObjectMother<Trigger> {
    fun create(
        id: String? = null,
        title: String? = null,
        input: String? = null,
        compare: String? = null,
        kind: String? = null,
        responseTextId: String? = null,
        responseAudioId: String? = null,
        guildId: String? = null,
    ) = Trigger.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        title = title ?: Random.instance().chiquito.terms(),
        input = input ?: Random.instance().chiquito.expressions(),
        compare = compare ?: Trigger.TriggerCompare.values().random().toString(),
        kind = kind ?: Random.instance().random.nextEnum(TriggerKind::class.java).value,
        responseTextId = responseTextId ?: Random.instance().random.nextUUID(),
        responseAudioId = responseAudioId ?: Random.instance().random.nextUUID(),
        guildId = guildId ?: Random.instance().random.nextUUID(),
    )

    override fun random(): Trigger = create()
}
